package andrey.code.manager.entity;

public record Product(
        Long id,
        String name,
        Integer price,
        String somePrivateInfo) {
}
