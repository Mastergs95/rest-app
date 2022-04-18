package com.jaxrswebservice;

import com.applicationentitites.Book;
import com.applicationentitites.BookDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/bookservice")
public class BookService {

    @GET
    @Path("/getbooks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBooks(){

        List<Book> listOfBooks= BookDAO.getAllBooks();

        return Response.status(Response.Status.OK).entity(listOfBooks).build();
    }

    @GET
    @Path("/getbooks/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("param")String bookId){

        Book book = BookDAO.getBookId(bookId);

        if(book==null){

            String jsonResponse = "{\"message\": \"A book with the given ID does not exists\","+
                    "\"bookId\": \"" + bookId + "\"}";

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(jsonResponse)
                    .build();
        }

        return Response.status(Response.Status.OK).entity(book).build();
    }

    @POST
    @Path("/addbook")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBook(Book book){

        String addMsg=BookDAO.addBook(book);

        if(addMsg.startsWith("Error")){

            String jsonResponse = "{\"message\": \"The book could not be loaded\","+
                    "\"message\": \"" + addMsg + "\"}";

            return Response.status(Response.Status.CONFLICT)
                    .entity(jsonResponse)
                    .build();
        }
        return Response.status(Response.Status.CREATED).entity(book).build();

    }

    @POST
    @Path("/updatebook")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBook(Book book){

        String addMsg=BookDAO.updateBook(book);

        if(addMsg.startsWith("Error")){

            String jsonResponse = "{\"message\": \"The book could not be located\","+
                    "\"message\": \"" + addMsg + "\"}";

            return Response.status(Response.Status.CONFLICT)
                    .entity(jsonResponse)
                    .build();
        }
        return Response.status(Response.Status.ACCEPTED).entity(book).build();

    }

    @POST
    @Path("/removebook/{param}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.TEXT_PLAIN})
    public Response deleteBook(@PathParam("param")String bookId){

        String deleteMsg=BookDAO.removeBook(bookId);

        if(deleteMsg.startsWith("Error")){

            String jsonResponse = "{\"message\": \"The book could not be removed\","+
                    "\"message\": \"" + deleteMsg + "\"}";

            return Response.status(Response.Status.NOT_FOUND)
                    .entity(jsonResponse)
                    .build();
        }
        return Response.status(Response.Status.ACCEPTED).
                entity("Book deleted. ID: " + bookId).build();

    }
}
