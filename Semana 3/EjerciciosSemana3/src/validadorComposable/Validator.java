package validadorComposable;

import java.util.function.Predicate;

@FunctionalInterface
interface Validator<T> {
    ValidationResult validate(T value);

    default Validator<T> and(Validator<T> other) {
        // Retorna un nuevo validador que ejecuta ambos y mezcla sus resultados
        return value -> {
            ValidationResult r1 = this.validate(value);
            ValidationResult r2 = other.validate(value);
            return r1.merge(r2);
        };
    }

    static <T> Validator<T> from(Predicate<T> predicate, String errorMsg) {
        // Si el predicado se cumple es válido, si no, es inválido con el mensaje
        return value -> predicate.test(value) 
                             ? ValidationResult.valid() 
                             : ValidationResult.invalid(errorMsg);
    }
}