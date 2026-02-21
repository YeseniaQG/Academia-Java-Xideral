package com.academia.batch.config;

import com.academia.batch.model.Empleado;
import com.academia.batch.processor.EmpleadoProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // ---------- READER: lee el archivo CSV ----------
    @Bean
    public FlatFileItemReader<Empleado> leerCsv() {
        return new FlatFileItemReaderBuilder<Empleado>()
                .name("empleadoReader")
                .resource(new ClassPathResource("empleados.csv"))
                .delimited()                          // separado por comas
                .names("nombre", "departamento", "salario") // columnas del CSV
                .targetType(Empleado.class)            // mapea a nuestro POJO
                .linesToSkip(1)                        // saltar la linea de encabezado
                .build();
    }

    // ---------- PROCESSOR: transforma cada registro ----------
    @Bean
    public EmpleadoProcessor procesarEmpleado() {
        return new EmpleadoProcessor();
    }

    // ---------- WRITER: escribe en la tabla MySQL ----------
    @Bean
    public JdbcBatchItemWriter<Empleado> escribirEnBd(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Empleado>()
                .sql("INSERT INTO empleados_procesados (nombre, departamento, salario, bono) " +
                     "VALUES (:nombre, :departamento, :salario, :bono)")
                .dataSource(dataSource)
                .beanMapped()   // usa los getters del POJO para mapear :nombre, :salario, etc.
                .build();
    }

    // ---------- STEP: un paso = reader + processor + writer ----------
    @Bean
    public Step paso1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Empleado> leerCsv,
                      EmpleadoProcessor procesarEmpleado,
                      JdbcBatchItemWriter<Empleado> escribirEnBd) {

        return new StepBuilder("paso1", jobRepository)
                .<Empleado, Empleado>chunk(3, transactionManager)  // procesa de 3 en 3
                .reader(leerCsv)
                .processor(procesarEmpleado)
                .writer(escribirEnBd)
                .build();
    }

    // ---------- JOB: el trabajo completo ----------
    @Bean
    public Job procesarEmpleadosJob(JobRepository jobRepository, Step paso1) {
        return new JobBuilder("procesarEmpleadosJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // run.id auto-incremental para permitir re-ejecuciones
                .start(paso1)   // inicia con el paso1
                .build();
    }
}
