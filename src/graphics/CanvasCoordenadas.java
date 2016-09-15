package graphics;
/**
 * @author Jonathan
 * @turma SI
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CanvasCoordenadas extends JPanel{
	
	private final int LARGURA_PANEL = 450;
	private final int ALTURA_PANEL	= 400;
	
	private final int ESPACAMENTO = 20;

	private ArrayList<Ellipse2D> cidades;
	private ArrayList<Line2D> caminhos;
	
	Graphics2D graphics;
	
	public CanvasCoordenadas() {
		super();
		cidades = new ArrayList<Ellipse2D>();
		caminhos = new ArrayList<Line2D>();
	}
	
	public void limparCidades(){
		cidades = new ArrayList<Ellipse2D>();
	}
	
	public void limparCaminhos(){
		caminhos = new ArrayList<Line2D>();
	}
	
	public void desenharCidade( int x, int y){
		Ellipse2D circulo = new Ellipse2D.Double( getXEllipse(x), getYEllipse(y), ESPACAMENTO / 2 , ESPACAMENTO / 2 );
		cidades.add(circulo);
	}
	
	public void desenharCaminho(int x1, int y1, int x2, int y2){
		Line2D caminho = new Line2D.Double( getXLine(x1), getYLine(y1), getXLine(x2), getYLine(y2) );
		caminhos.add(caminho);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		graphics = (Graphics2D) g;
		
		limpar();
		desenharCoordenadas();
		desenharCaminhos();
		desenharCidades();
	}
	
	private void limpar(){
		graphics.clearRect(0, 0 , LARGURA_PANEL, ALTURA_PANEL );
	}
	
	private void desenharCoordenadas(){
		int i,
		centroX = LARGURA_PANEL / 2,
		centroY = ALTURA_PANEL / 2;

		Line2D linha,
			coluna;
		
		Stroke drawingStroke = new BasicStroke(3);
		
		//Desenha as colunas
	    for ( i = 0; i < centroX; i += ESPACAMENTO) {
	    		    	
	    	linha = new Line2D.Double( centroX + i, 0, centroX + i, ALTURA_PANEL );
	    	graphics.draw( linha );
	    	
	    	graphics.drawString( String.valueOf( i / ESPACAMENTO ), centroX + i + ESPACAMENTO / 5, (int) (centroY + ESPACAMENTO / 1.5));
	    	
	    	linha = new Line2D.Double( centroX - i, 0, centroX - i, ALTURA_PANEL );
	    	graphics.draw( linha );
	    	
	    	if(i != 0)
	    		graphics.drawString( String.valueOf( i / ESPACAMENTO * -1 ), centroX - i, (int) (centroY + ESPACAMENTO / 1.5));
		}

		//Desenha as linhas
	    for ( i = 0 ; i < centroY; i += ESPACAMENTO) {
	    	coluna = new Line2D.Double(0 , centroY + i , LARGURA_PANEL, centroY + i );
	    	graphics.draw( coluna );
	    	
	    	if(i != 0)
	    		graphics.drawString( String.valueOf( i / ESPACAMENTO ), (int) (centroX - ESPACAMENTO / 1.5), (int) (centroY - i + ESPACAMENTO / 1.5));
	    	
	    	coluna = new Line2D.Double(0 ,centroY - i , LARGURA_PANEL, centroY - i );
	    	graphics.draw( coluna );
	    	
	    	if(i != 0)
	    		graphics.drawString( String.valueOf( i / ESPACAMENTO * -1 ), (int) (centroX - ESPACAMENTO / 1.5), (int) (centroY + i + ESPACAMENTO / 1.5));
		}
	    
	   //Desenha o eixo Y 
    	coluna = new Line2D.Double( centroX, 0, centroX, ALTURA_PANEL );
    	graphics.setPaint( Color.green );
    	graphics.setStroke(drawingStroke);
    	graphics.draw( coluna );
    	
    	//Desenha o eixo X
    	linha = new Line2D.Double( 0, centroY, LARGURA_PANEL, centroY );
    	graphics.setPaint( Color.red );
    	graphics.setStroke(drawingStroke);
    	graphics.draw( linha );
	}
	
	private void desenharCidades(){
		for (Ellipse2D circulo : cidades) {

			graphics.setPaint( Color.blue );
			graphics.fill( circulo );
		    graphics.draw( circulo );
		    
		}
	}
	
	private void desenharCaminhos(){
		Stroke drawingStroke = new BasicStroke(2);
		
		for (Line2D caminho : caminhos){
			
			graphics.setPaint( Color.orange );
			graphics.setStroke(drawingStroke);
			graphics.draw( caminho );
			
		}
	}
	
	
	private int getXEllipse( int x ){
		return (LARGURA_PANEL / 2) + (x * ESPACAMENTO) - (ESPACAMENTO / 5);
	}
	
	private int getYEllipse( int y ){
		return (ALTURA_PANEL / 2) - (y * ESPACAMENTO) - (ESPACAMENTO / 5);
	}
	
	private int getXLine( int x ){
		return (LARGURA_PANEL / 2) + (x * ESPACAMENTO);
	}
	
	private int getYLine( int y ){
		return (ALTURA_PANEL / 2) - (y * ESPACAMENTO);
	}
}
