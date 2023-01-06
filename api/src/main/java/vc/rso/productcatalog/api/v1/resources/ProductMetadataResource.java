package vc.rso.productcatalog.api.v1.resources;


import DTOs.ProductDTO;
import entities.Product;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import services.beans.ProductMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;


@ApplicationScoped
@Path("/products")
@Tag(name = "Products") //@Tag namesto default zapisano ime
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductMetadataResource {

    private Logger log = Logger.getLogger(Product.class.getName());

    @Inject
    private ProductMetadataBean productMetadataBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all ProductDTOs.", summary = "Get all products")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of productDTOs",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count",
                            description = "Number of objects in list",
                            schema = @Schema(type = SchemaType.INTEGER))}
            )})
    @GET
    public Response getProducts() {
        List<ProductDTO> products = productMetadataBean.getProductsFilter(uriInfo);
        return Response.status(Response.Status.OK).entity(products).build();
    }


    @Operation(description = "Get metadata for an product.", summary = "Get metadata for an product")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Product metadata",
                    content = @Content(
                            schema = @Schema(implementation = ProductDTO.class))
            )})
    @GET
    @Path("/{productId}")
    public Response getProduct(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("productId") Integer productId) {

        ProductDTO dto = productMetadataBean.getProduct(productId);

        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @Operation(description = "Add product to database.", summary = "Add product")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Product successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error.")
    })
    @POST
    public Response createProduct(@RequestBody(
            description = "DTO object with product info.",
            required = true, content = @Content(
            schema = @Schema(implementation = ProductDTO.class))) ProductDTO dto) {

        if (dto.getTitle() == null || dto.getDescription() == null
            || dto.getCategory() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            dto = productMetadataBean.createProduct(dto);
        }

        return Response.status(Response.Status.CONFLICT).entity(dto).build();

    }


    @Operation(description = "Update product.", summary = "Update Product")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{productId}")
    public Response putProduct(@Parameter(description = "Product ID.", required = true)
                                     @PathParam("productId") Integer productId,
                                     @RequestBody(
                                             description = "ProductDTO object.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = ProductDTO.class)))
                                     ProductDTO dto){

        dto = productMetadataBean.putProduct(productId, dto);

        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();

    }

    @Operation(description = "Delete product from database.", summary = "Delete product")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Product successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{productId}")
    public Response deleteProduct(@Parameter(description = "Product ID.", required = true)
                                        @PathParam("productId") Integer productId){

        boolean deleted = productMetadataBean.deleteProduct(productId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
