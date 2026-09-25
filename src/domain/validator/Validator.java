package domain.validator;

public interface Validator<T> {
    void validate(T value);
}
