package com.academia.batch.config;

import com.academia.batch.model.Producto;
import com.academia.batch.model.ProductoReporte;
import com.academia.batch.processor.ProductoProcessor;
import com.academia.batch.processor.ReporteProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    // =====================================================================
    //  STEP 1: Lee CSV → procesa (bono + mayusculas) → escribe en MySQL
    // =====================================================================

    @Bean
    public FlatFileItemReader<Producto> leerCsv() {
        return new FlatFileItemReaderBuilder<Producto>()
                .name("ProductoReader")
                .resource(new ClassPathResource("productos.csv"))
                .delimited()                          // separado por comas
                .names("marca", "nombreProducto", "precio", "quantity","descuento") // columnas del CSV
                .targetType(Producto.class)            // mapea a nuestro POJO
                .linesToSkip(1)                        // saltar la linea de encabezado
                .build();
    }

    @Bean
    public ProductoProcessor procesarProducto() {
        return new ProductoProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Producto> escribirEnBd(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Producto>()
                .sql("INSERT INTO productos_procesados (marca, nombreProducto, precio, quantity, descuento) " +
                     "VALUES (:marca, :nombreProducto, :precio, :quantity, :descuento)")
                .dataSource(dataSource)
                .beanMapped()   // usa los getters del POJO para mapear :nombre, :salario, etc.
                .build();
    }

    @Bean
    public Step paso1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<Producto> leerCsv,
                      ProductoProcessor procesarProducto,
                      JdbcBatchItemWriter<Producto> escribirEnBd) {

        return new StepBuilder("paso1", jobRepository)
                .<Producto, Producto>chunk(3, transactionManager)
                .reader(leerCsv)
                .processor(procesarProducto)
                .writer(escribirEnBd)
                .build();
    }

    // =====================================================================
    //  STEP 2: Lee MySQL → calcula salario total → escribe en MongoDB
    // =====================================================================

    @Bean
    public JdbcCursorItemReader<Producto> leerDeBd(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Producto>()
                .name("productoDbReader")
                .dataSource(dataSource)
                .sql("SELECT marca, nombreProducto, precio, quantity, descuento FROM productos_procesados")
                .rowMapper((rs, rowNum) -> {
                    Producto producto = new Producto();
                    producto.setMarca(rs.getString("marca"));
                    producto.setNombreProducto(rs.getString("nombreProducto"));
                    producto.setPrecio(rs.getBigDecimal("precio"));
                    producto.setQuantity(rs.getInt("quantity"));
                    producto.setDescuento(rs.getBigDecimal("descuento"));
                    return producto;
                })
                .build();
    }

    @Bean
    public ReporteProcessor procesarReporte() {
        return new ReporteProcessor();
    }

    @Bean
    public MongoItemWriter<ProductoReporte> escribirEnMongo(MongoTemplate mongoTemplate) {
        return new MongoItemWriterBuilder<ProductoReporte>()
                .template(mongoTemplate)
                .collection("reportes")
                .build();
    }

    @Bean
    public Step paso2(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      JdbcCursorItemReader<Producto> leerDeBd,
                      ReporteProcessor procesarReporte,
                      MongoItemWriter<ProductoReporte> escribirEnMongo) {

        return new StepBuilder("paso2", jobRepository)
                .<Producto, ProductoReporte>chunk(3, transactionManager)
                .reader(leerDeBd)
                .processor(procesarReporte)
                .writer(escribirEnMongo)
                .build();
    }

    // =====================================================================
    //  JOB: ejecuta paso1 y luego paso2
    // =====================================================================

    @Bean
    public Job procesarProductosJob(JobRepository jobRepository, Step paso1, Step paso2) {
        return new JobBuilder("procesarProductosJob", jobRepository)
                .incrementer(new RunIdIncrementer()) // run.id auto-incremental para permitir re-ejecuciones
                .start(paso1)
                .next(paso2)    // despues de paso1, ejecuta paso2
                .build();
    }
}
