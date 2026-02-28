package validadorComposable;

import java.util.*;

record ValidationResult(boolean isValid, List<String> errors) {
    static ValidationResult valid() {
        return new ValidationResult(true, List.of());
    }

    static ValidationResult invalid(String... errors) {
        return new ValidationResult(false, List.of(errors));
    }

    ValidationResult merge(ValidationResult other) {
        // Si ambos son válidos, el resultado sigue siendo válido
        if (this.isValid && other.isValid) return valid();
        
        // Si alguno es inválido, combinamos las listas de errores
        List<String> allErrors = new ArrayList<>(this.errors);
        allErrors.addAll(other.errors);
        return new ValidationResult(false, Collections.unmodifiableList(allErrors));
    }
}