package nvb.dev.usermanagementdemo.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class<?> entity, Long id) {
        super(String.format("The %s with id %d does not exist.", entity.getSimpleName().toLowerCase(), id));
    }

}
