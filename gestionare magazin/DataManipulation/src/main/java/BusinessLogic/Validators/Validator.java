package BusinessLogic.Validators;

public interface Validator<T> {
    public boolean validate(T t);
}
