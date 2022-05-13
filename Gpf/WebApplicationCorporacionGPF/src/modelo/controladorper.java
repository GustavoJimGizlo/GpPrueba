package modelo;

import java.util.*;
import modelo.personadao;
import modelo.persona;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Servlet implementation class controladorper
 */
@WebServlet("/controladorper")
public class controladorper extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	personadao daop=new personadao();
    persona p=new persona();
    
	
	
    public controladorper() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        // agregado por sfba
		String vaccion=request.getParameter("accion");
		//switch (vaccion) {
		if (vaccion == "ListarPersonas")
		{
		//case "ListarPersonas":
			List<persona>datos=daop.listar();
			request.setAttribute("datos", datos);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
		if (vaccion == "NuevaPersona")
		{
			request.getRequestDispatcher("add.jsp").forward(request, response);
		}
		
		/*case "Grabar":
			String vid=request.getParameter("txtid");
			String vnombre=request.getParameter("txtnombre");
			String vcedula=request.getParameter("txtcedula");
			String vcorreo=request.getParameter("txtcorreo");
			
			p.setId(vid);
			p.setNombre(vnombre);
			p.setCedula(vcedula);
			p.setCorreo(vcorreo);
			daop.agregar(p);
			request.getRequestDispatcher("controladorper?accion=ListarPersonas").forward(request, response);
			
			
		case "Editar":
			
			String par_id =request.getParameter("p_id");
			
			
			
			persona p=daop.listarid(par_id);
			
			request.setAttribute("persona", p);
			
			request.getRequestDispatcher("edit.jsp").forward(request, response);
			break;
		    default:
			throw new AssertionError();
		}*/
		
	}

}
