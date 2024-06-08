package andrey.code.manager.controller.payload;


public record ProductPayload(

        String name,

        Integer price,

        String somePrivateInfo) {
}
