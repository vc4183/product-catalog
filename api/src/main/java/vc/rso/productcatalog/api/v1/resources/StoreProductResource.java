package vc.rso.productcatalog.api.v1.resources;

import DTOs.StoreProductDTO;
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
import services.beans.StoreProductBean;
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
@Path("/storeProducts")
@Tag(name = "StoreProducts") //@Tag namesto default zapisano ime
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StoreProductResource {

    private Logger log = Logger.getLogger(Product.class.getName());

    @Inject
    private StoreProductBean storeProductBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all StoreProductDTOs.", summary = "Get all StoreProducts")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of storeProducts",
                    content = @Content(schema = @Schema(implementation = StoreProductDTO.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count",
                            description = "Number of objects in list",
                            schema = @Schema(type = SchemaType.INTEGER)
                    )}
            )})
    @GET
    public Response getAll() {
        List<StoreProductDTO> storeProducts = storeProductBean.getFilter(uriInfo);
        return Response.status(Response.Status.OK).entity(storeProducts).build();
    }


    @Operation(description = "Get metadata for a StoreProduct.", summary = "Get metadata for a StoreProduct")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "StoreProduct metadata",
                    content = @Content(
                            schema = @Schema(implementation = StoreProductDTO.class))
            )})
    @GET
    @Path("/{storeProductId}")
    public Response getById(@Parameter(description = "StoreProduct ID.", required = true)
                               @PathParam("storeProductId") Integer storeProductId) {

        StoreProductDTO dto = storeProductBean.getById(storeProductId);

        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(dto).build();
    }

    @Operation(description = "Add StoreProduct to database.", summary = "Add StoreProduct")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "StoreProduct successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error.")
    })
    @POST
    public Response add(@RequestBody(
            description = "DTO object with StoreProduct info.",
            required = true, content = @Content(
            schema = @Schema(implementation = StoreProductDTO.class))) StoreProductDTO dto) {

        if (dto.getProductId() == null || dto.getStoreId() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            dto = storeProductBean.create(dto);
        }

        return Response.status(Response.Status.CONFLICT).entity(dto).build();

    }


    @Operation(description = "Update StoreProduct.", summary = "Update StoreProduct")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "StoreProduct successfully updated."
            )
    })
    @PUT
    @Path("{storeProductId}")
    public Response put(@Parameter(description = "StoreProduct ID.", required = true)
                               @PathParam("storeProductId") Integer storeProductId,
                               @RequestBody(
                                       description = "StoreProductDTO object.",
                                       required = true, content = @Content(
                                       schema = @Schema(implementation = StoreProductDTO.class)))
                               StoreProductDTO dto){

        System.out.println("Update store product");
        dto = storeProductBean.put(storeProductId, dto);

        if (dto == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).build();

    }

    @Operation(description = "Delete StoreProduct from database.", summary = "Delete StoreProduct")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "StoreProduct successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{storeProductId}")
    public Response delete(@Parameter(description = "Product ID.", required = true)
                                  @PathParam("storeProductId") Integer storeProductId){

        boolean deleted = storeProductBean.delete(storeProductId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
