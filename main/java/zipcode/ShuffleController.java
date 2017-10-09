package zipcode;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import shuffle.cards.Card;
import shuffle.cards.Deck;

import shuffle.cards.Deck;
import shuffle.cards.Card;

import java.io.IOException;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Context;

@RestController
public class ShuffleController {
	

	  HashMap<String,Deck> decks = new HashMap<String,Deck>();
	  HttpSession session;
	  Deck<Card> deck;
	  @Context private HttpServletRequest request;


//	  @GET
//	  @Path("/get/deck/{deckName}")
		@RequestMapping(value = "/get/deck/{deckName}", method = RequestMethod.GET)
	  public Response getDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest request){
			
			
//	    ServletContext ctx = request.getSession().getServletContext();
//	    String output;
//	    if(ctx != null){
//	      Deck<Card> deck = (Deck<Card>)ctx.getAttribute("deck_" + deckName); 
//	      if(deck != null){
//	        output = convertToJson((Deck<Card>)ctx.getAttribute("deck_" + deckName)) + "\n";
//	      }else{
//	        return Response.status(404).entity("Couldn't find deck.\n").build();
//	      }
//	    }else{
//	      return Response.status(404).entity("Couldn't find or instantiate context.").build();
//	    }
//	    return Response.status(200).entity(output).build();
			return Response.status(200).entity(deckName).build();
	  }
	  @GET
	  @Path("/get/alldecks")
	  public Response getDeck(@Context HttpServletRequest request){
	    ServletContext ctx = request.getSession().getServletContext();
	    List<Deck<Card>> decks = new LinkedList<Deck<Card>>();
	    String output = "";
	    if(ctx != null){
	      for (Enumeration e = ctx.getAttributeNames(); e.hasMoreElements() ;) {
	        //decks.add((Deck<Card>)ctx.getAttribute((String)e.nextElement()));
	        String deckString = e.nextElement().toString();
	        if(deckString.contains("deck")){
	         output = output + deckString + ":\n" + convertToJson((Deck<Card>)ctx.getAttribute(deckString)) + "\n\n";
	        }
	      }
	      for(Deck<Card> d : decks){
	        output = output + "[" + d.getDeckName() + "";
	      }
	    }else{
	      return Response.status(404).entity("Couldn't find or instantiate context.").build();
	    }
	    return Response.status(200).entity(output).build();
	  }

	  @PUT 
	  @Path("/put/deck/{deckName}")
	  public Response putDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest request){
	    ServletContext ctx = request.getSession().getServletContext();
	    String shuffleAlgorithm = (String)ctx.getAttribute("shuffleAlgorithm");
	    String output;
	    Deck<Card> deck;
	    if(ctx != null){
	      deck = new Deck<Card>(setupDeck(),shuffleAlgorithm);
	      deck.setDeckName(deckName);
	      ctx.setAttribute("deck_" + deckName,deck);
	      output = convertToJson((Deck<Card>)ctx.getAttribute("deck_" + deckName)) + "\n"; 
	    }else{
	      return Response.status(404).entity("Couldn't find servlet context.").build();
	    }
	    return Response.status(201).entity(output).build();
	  }
	  @POST
	  @Path("/post/shuffle/{deckName}")
	  public Response shuffleDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest request){
	    ServletContext ctx = request.getSession().getServletContext();
	    String output;
	    Deck<Card> deck;
	    if(ctx != null){
	      deck = (Deck<Card>)ctx.getAttribute("deck_" + deckName);
	      deck.shuffleDeck();
	      ctx.setAttribute("deck_" + deckName,deck);
	      output = convertToJson((Deck<Card>)ctx.getAttribute("deck_" + deckName)); 
	    }else{
	      return Response.status(404).entity("Couldn't find servlet context.").build();
	    }
	    return Response.status(200).entity(output).build();
	  }
	  @DELETE
	  @Path("/delete/deck/{deckName}")
	  public Response removeDeck(@PathParam("deckName") String deckName, @Context HttpServletRequest request){
	    ServletContext ctx = request.getSession().getServletContext();
	    String output;
	    Deck<Card> deck;
	    if(ctx != null){
	      ctx.removeAttribute("deck_" + deckName);
	      output = "Removed deck [" + deckName + "] from servlet context.\n";
	    }else{
	      return Response.status(404).entity("Couldn't find servlet context.").build();
	    }
	    return Response.status(200).entity(output).build();
	  }
	  @GET
	  @Path("/test")
	  public Response testDeck(@Context HttpServletRequest request){
	    ServletContext ctx = request.getSession().getServletContext();
	    String output = (String)ctx.getAttribute("shuffleAlgorithm"); 
	    return Response.status(200).entity(output).build();
	  } 

	  /*
	    =====================
	    Setup Deck
	    =====================
	  */
	  private List<Card> setupDeck(){
	    List<Card> llist = new LinkedList<Card>();
	    String[] faceCards = {"J","Q","K","A"};
	    String[] suits = {"spades","clubs","diamonds","hearts"};

	    for(int i=2; i<=10; i++){
	      for(int j=1; j<=4; j++){
	        llist.add(new Card(Integer.toString(i), suits[j-1], j, i));
	      }
	    }
	    for(int k=1; k<=4; k++){
	      for(int l=1; l<=4; l++){
	        llist.add(new Card(faceCards[k-1],suits[l-1], l, k+10));
	      }
	    }
	    return llist;
	  }
	  /*
	    =====================
	    Convert to JSON
	    =====================
	  */
	  private String convertToJson(Deck<Card> deck){
	    JSONObject jsonObj = new JSONObject();
	    List<Card> cards = deck.getDeck();
	    List<String> parsedCards = new LinkedList<String>();
	    int i = 1;
	    for(Card card: cards){
	      parsedCards.add(card.getCardValue() + "-" +  card.getSuit());
	      i++;
	    }

	    jsonObj.put("deck",parsedCards);
	    JSONArray jArray = jsonObj.getJSONArray("deck");
	    return jArray.toString();
	  }

	@ExceptionHandler(ZipCodeException.class)
	@RequestMapping(value = "/Get", method = RequestMethod.GET)
	public ZipCode getJson(@RequestParam("zipCodeList") int[] zipCodeList) throws ZipCodeException {
		validate(zipCodeList);
		ZipCode zipCode = new ZipCode(zipCodeList);
		zipCode.setFinalOutput(processZipCode(zipCode));
		return zipCode;

	}

	private void validate(int[] zipCodeList) throws ZipCodeException {
		if (zipCodeList.length < 0) {
			throw new ZipCodeException("please enter atleast one valid zip code, a zipcode size is 5 digits");
		} else {
			for (int zipcode : zipCodeList) {
				if (!(Integer.toString(zipcode).length() == 5)) {
					throw new ZipCodeException("please enter atleast one valid zip code, a zipcode size is 5 digits");
				}
			}
		}
	}

	private String processZipCode(ZipCode zipCode) {
		int[] zipCodeList = zipCode.getZipCodeList();
		Arrays.sort(zipCodeList);
		return "The maximum of the zip code list :" + zipCodeList[zipCodeList.length-1];
	}
}
