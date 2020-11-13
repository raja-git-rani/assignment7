package Assignment7.pkg;

//Import Java Libraries
import java.io.*;
import java.util.*;

//Import Servlet Libraries
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class Assn7
 */
@WebServlet("/Assn7")
public class Assn7 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 static String RESOURCE_FILE = "fileEntry.txt";
	  static final String VALUE_SEPARATOR = ";";	
	  // Button labels
	  static String OperationSubmit = "Calculate this Predicate";
	  static String OperationSubmit1 = "Submit";
	  static String OperationReset = "Reset";
	  static String OperationPredicate = "Show All Predicates On File";
      static Integer counter = 0;
  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Assn7() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		   PrintWriter out = response.getWriter();
		   PrintHead(out);
		   PrintBody(out, "", "", "","");
		   PrintTail(out);	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		   String val= "";
		   String err="";
		   String operation = request.getParameter("Operation");
		   String operation1 = request.getParameter("o1");
		   String bollean1 = request.getParameter("bol1");
		   String bollean2 = request.getParameter("bol2");
		   String id_val = request.getParameter("ids");
		 
		   String res="";
		   
		   
		   response.setContentType("text/html");
		   PrintWriter out = response.getWriter();
		   // if id_val is null
		   
		   if(id_val ==null){
			   if(!operation.equals(OperationPredicate)){
				if(bollean1 !=null && bollean2 !=null && operation1 !=null){
		   
					if(bollean1.equals("")){
						err= "<li>Boolean 1 is required</li>";	
						bollean1="";
	    	   
					}
					else if(operation1.equals("")){
						err= "<li>Operator is required</li>";
						operation1="";
					}
					else if(bollean2.equals("")){
						err= "<li>Boolean 2 is required</li>";
						bollean2="";
					}
	     
	       	    		   
					else if(operation1.equals("&&") || operation1.equals("AND"))
						val= "AND";
					else if(operation1.equals("OR") || operation1.equals("||"))
						val= "OR";
					else {
						err="<li>Operator has to be either AND,OR,&&,||</li> ";
						operation1="";
					}	
	       
			
					if(operation.equals(OperationReset)){
						bollean1="";
						bollean2="";
						operation1="";
						err="";
						res="Reset";
					}
		
				}//end if null
				

			     if (err.equals("") && res.equals("")){
			    	 PrintWriter entriesPrintWriter = new PrintWriter(new FileWriter(RESOURCE_FILE, true), true);
				      entriesPrintWriter.print("");
				      String ID_String = "";
				      ID_String = getId(out,RESOURCE_FILE);
				      int ID_int = Integer.parseInt(ID_String);
				      ID_int++;
					  entriesPrintWriter.println(ID_int+VALUE_SEPARATOR+bollean1+VALUE_SEPARATOR+operation1+ VALUE_SEPARATOR+bollean2);
					  entriesPrintWriter.close();
					  PrintTab(out,bollean1,bollean2,val,err);   
				      PrintTail(out);
			     }else{
				       PrintHead(out);
				       PrintBody(out, bollean1, bollean2, operation1, err);
				       PrintTail(out);
				     }
				
				
		}// end if operation is not a predicate
		   //if operation is a predicate
		else{
		
		    	   PrintHead(out);
			       PrintResponseBody(out, RESOURCE_FILE);
			       printBody1(out);
			   
		}
		   }
	 
	//end of if id_val == null
		  //id_val no longer null
 			  //PrintResponseBody(out, RESOURCE_FILE);
		   
		else{   
		   
		 File file = new File(RESOURCE_FILE);
		 try {
			 if(!file.exists()){
				  out.println(" <table>");
		          out.println("  <tr>");
		          out.println("   <td>No entries persisted yet.</td>");
		          out.println("  </tr>");
		          out.println(" </table>");
		          return;
		        }
			 BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = bufferedReader.readLine()) != null) {
		          String []  entry= line.split(VALUE_SEPARATOR);
		        
		          for(String value: entry){
		              if(value.equals(id_val)){
		            	
		            	  String fBol = entry[1];
		            	  String op = entry[2];
		            	  String sBol = entry[3];
		            	  String valu ="";
		            	  if(op.equals("&&") || op.equals("AND"))
								valu= "AND";
		            	  else if(op.equals("OR") || op.equals("||"))
								valu= "OR";
		            	  PrintTab(out, fBol, sBol, valu,"");  
		            	  bufferedReader.close();
		            	  return;
		              }
		          }
		          
		        }
		        bufferedReader.close();
		      } catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		 out.println(" <table>");
         out.println("  <tr>");
         out.println("   <td>Id did not match the entries</td>");
         out.println("  </tr>");
         out.println(" </table>");
			 
		}//end of if id_val !=null
		 
		   	   
	
			 
	}
	
	
	
	private void PrintTab(PrintWriter out, String bol_1, String bol_2, String value,String errrs) {
		// TODO Auto-generated method stub
		
		if(errrs.equals("")){
		out.println("<body onLoad=\"setFocus()\">");
	    out.println("<p>");
		out.println("A simple example that demonstrates how to operate with");
		out.println("Boolean variables");
		out.println("By Rajaram Adhikari & Kshitij Rimal");
		out.println("</p>");
		
		out.println(" <table BORDER=1  CELLPADDING=0 CELLSPACING=0 WIDTH=50% >");
	       out.println("  <tr>");
	       out.println("   <th>Row</th>");
	       out.println("   <th>"+bol_1+"</th>");
	       out.println("   <th>"+bol_2+" </th>");
	       out.println("   <th>Result</th>");
	       out.println("  </tr>");
	       
	       
	       if(value.equals("AND")){
	    	   
	    	   out.println("  <tr>");
	    	   out.println("   <td>1");
	    	   out.println("   <td>false");
	    	   out.println("   <td>false");
	    	   out.println("   <td>false");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	   
	    	   out.println("  <tr>");
	    	   out.println("   <td>2");
	    	   out.println("   <td>false");
	    	   out.println("   <td>true");
	    	   out.println("   <td>false");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	 
	    	   out.println("  <tr>");
	    	   out.println("   <td>3");
	    	   out.println("   <td>true");
	    	   out.println("   <td>false");
	    	   out.println("   <td>false");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	   
	    	   
	    	   out.println("  <tr>");
	    	   out.println("   <td>4");
	    	   out.println("   <td>true");
	    	   out.println("   <td>true");
	    	   out.println("   <td>true");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	   
	    	   
	    	   
	       }
	       else if(value.equals("OR")){
	    	   
	    	   
	    	   out.println("  <tr>");
	    	   out.println("   <td>1");
	    	   out.println("   <td>false");
	    	   out.println("   <td>false");
	    	   out.println("   <td>false");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	   
	    	   out.println("  <tr>");
	    	   out.println("   <td>2");
	    	   out.println("   <td>false");
	    	   out.println("   <td>true");
	    	   out.println("   <td>true");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	 
	    	   out.println("  <tr>");
	    	   out.println("   <td>3");
	    	   out.println("   <td>true");
	    	   out.println("   <td>false");
	    	   out.println("   <td>true");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	   
	    	   
	    	   out.println("  <tr>");
	    	   out.println("   <td>4");
	    	   out.println("   <td>true");
	    	   out.println("   <td>true");
	    	   out.println("   <td>true");
	    	   out.println("  </tr>");
	    	   out.println(" <br>");
	    	   //
	       	
	      }
	       
	    
	    
	    
	     out.println(" </table>");
	     out.println("");
	
	     out.println("<p>");

		out.println(" Rajaram and Kshitiz collaborated on this assignment. Kshitiz worked on storing user input to file and Rajaram worked on displaying the truth table by fetching the stored data in file.  ");
		
		out.println("</p>");
		
			out.println("</p>");
	     out.println("</body>");
		}
	
		
	}

	private void PrintHead (PrintWriter out)
	{
	   out.println("<html>");
	   out.println("");

	   out.println("<head>");
	   out.println("<title>Boolean example</title>");
	   out.println("</head>");
	   out.println("");
	} // End PrintHead
	
	private void PrintBody (PrintWriter out, String bol_1, String bol_2,  String operation_1,String errors){
	    
	     out.println("<body onLoad=\"setFocus()\">");
	     out.println("<p>");
	     out.println("A simple example that demonstrates how to operate with");
			out.println("Boolean variables");
			out.println("By Rajaram Adhikari & Kshitiz Rimal");
	     out.println("</p>");
	     
	     
	     
	    	 if(!errors.equals("")){
		       out.println("<p style=\"color:red;\">Please correct the following and resubmit.</p>");
		       out.println("<ol>");
		       out.println(errors);
		       out.println("</ol>");
		     }
	     
	   
	   out.print  ("<form method=\"post\"");
	   out.println("");
	   out.println(" <table>");
	   
	   out.println("  <tr>");
	   out.println("   <td>Enter the first Boolean:");
	   out.println("   <td><input type=\"text\" name=\"bol1\" value=\"" + bol_1 + "\" size=5>");
	   out.println("  </tr>");
	   out.println(" <br>");
	   
	   
	   out.println("  <tr>");
	   out.println("   <td> Choose the operator:");
	   out.println("   <td><input type=\"text\" name=\"o1\" value=\"" + operation_1+  "\" size=5>");
	   out.println("  </tr>");
	   out.println(" <br>");

	   out.println("  <tr>");
	   out.println("   <td>Enter the second Boolean:");
	   out.println("   <td><input type=\"text\" name=\"bol2\" value=\"" + "\" size=5>");
	   out.println("  </tr>"); 
	   
	   out.println(" </table>");
	   
	   out.println(" <br>");
	   out.println(" <br>");
	   out.println(" <input type=\"submit\" value=\"" + OperationSubmit + "\" name=\"Operation\">");
	   out.println(" <input type=\"submit\" value=\"" + OperationPredicate
		   	      + "\" name=\"Operation\">");
	   out.println(" <input type=\"submit\" value=\"" + OperationReset + "\" name=\"Operation\">");
	
	   //out.println(" <input type=\"reset\" value=\"Reset\" name=\"reset\">");
	   out.println("</form>");
	   out.println("");
	   out.println("</body>");
	  
	     
 
	} // End PrintBody
	
	

	void printBody1(PrintWriter out){
		
		         out.println("<body>");
			     out.print  ("<form method=\"post\"");
			     out.println("");
			     out.println(" <table>");
			     out.println("  <tr>");
				   
				 out.println("   <td>Enter ID to print the truth table from the list:");
			     out.println(" <td><input type=\"text\" name=\"ids\" value=\""  + "\" size=5>"); 
			     out.println(" <input type=\"submit\" value=\"" + OperationSubmit1 + "\" name=\"Operation\">");
			     out.println("  </tr>");
			     out.println(" </table>");
				 out.println("</form>");
				 out.println("");
				 out.println("</body>");
			   
		   
	}
	
	
	

	
	private void PrintTail (PrintWriter out)
	{
	   out.println("");
	   out.println("</html>");
	}
	
	
	 private void PrintResponseBody (PrintWriter out, String resourcePath){
		    out.println("<body onLoad=\"setFocus()\">");
		    out.println("<p>");
		    out.println("Predicate Table");
		    out.println("</p>");
		    out.println("");
		    
		 
		    out.println(" <table>");

		    try {
		        out.println("  <tr>");
		        out.println("   <th>ID</th>");
		        out.println("   <th>First Boolean</th>");
		        out.println("   <th>Operator</th>");
		        out.println("   <th>Second Boolean</th>");
		        out.println("  </tr>");
		        File file = new File(resourcePath);
		        if(!file.exists()){
		          out.println("  <tr>");
		          out.println("   <td>No entries persisted yet.</td>");
		          out.println("  </tr>");
		          return;
		        }

		        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = bufferedReader.readLine()) != null) {
		          String []  entry= line.split(VALUE_SEPARATOR);
		          out.println("  <tr>");
		          for(String value: entry){
		              out.println("   <td>"+value+"</td>");
		          }
		          out.println("  </tr>");
		        }
		        bufferedReader.close();
		      } catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		   
		     out.println(" </table>");
		     out.println("</body>");
		    
		    
		  }
	 
	public String getId(PrintWriter out,String resourcepath) {
		String id= "0";
		File file = new File(resourcepath);
		 try {
			 if(!file.exists()){
				  out.println(" <table>");
		          out.println("  <tr>");
		          out.println("   <td>No entries persisted yet.</td>");
		          out.println("  </tr>");
		          out.println(" </table>");
		          return id;
		        }
			 BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
		        String line;
		        while ((line = bufferedReader.readLine()) != null) {
		          String []  entry= line.split(VALUE_SEPARATOR);
		          id= entry[0];
		    
		          
		        }
		        bufferedReader.close();
		      } catch (FileNotFoundException ex) {
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }

		return id;	 
		}

	
	

}
