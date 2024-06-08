package andrey.code.rest.mapper;

import andrey.code.rest.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "price", target = "price")
    ProductDTO toProductDTO(ProductEntity product);

    List<ProductDTO>toProductDTOList(List<ProductEntity> products);
}
