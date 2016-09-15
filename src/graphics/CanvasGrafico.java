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
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CanvasGrafico extends JPanel{
	
	private final int LARGURA_PANEL = 380;
	private final int ALTURA_PANEL	= 380;
	
	private final int PADDING_X = 20;
	private final int PADDING_Y = 20;
	
	private ArrayList<Double> fitnessList;
	
	private Graphics2D graphics;
	
	private double melhorFitness;
	
	private int fitnessMaximoGrafico,
    	tempoMaximoGrafico;
	
	public CanvasGrafico() {
		super();
		fitnessList = new ArrayList<Double>();
		melhorFitness = 0;	
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		graphics = (Graphics2D) g;
		
		limpar();
		desenharCoordenadas();
	}
	
	private void limpar(){
		graphics.clearRect(0, 0 , LARGURA_PANEL, ALTURA_PANEL );
	}
	
	public void limparAndamentoGrafico(){
		fitnessList = new ArrayList<Double>();
	}
	
	private void desenharCoordenadas(){
		Line2D linha,
		coluna;

		Stroke drawingStroke = new BasicStroke(3);
		/*
		//Desenha as colunas
	    for ( i = PADDING_X; i < LARGURA_PANEL; i += ESPACAMENTO) {
	    	linha = new Line2D.Double( i, 0, i, ALTURA_PANEL );
	    	graphics.draw( linha );
		}

		//Desenha as linhas
	    for ( i = ALTURA_PANEL - PADDING_Y ; i >= 0; i -= ESPACAMENTO) {
	    	coluna = new Line2D.Double(0 ,i , LARGURA_PANEL, i );
	    	graphics.draw( coluna );
		}
		*/
	    fitnessMaximoGrafico = (int) melhorFitness + 1;
	    tempoMaximoGrafico = (int) fitnessList.size();
	    
	    //Mostrando Fitness Maximo
	    graphics.drawString( String.valueOf( fitnessMaximoGrafico ), PADDING_X/10, PADDING_Y);

	    //Mostrando Geracao Maxima
	    graphics.drawString( String.valueOf( tempoMaximoGrafico ), LARGURA_PANEL-PADDING_X*2, ALTURA_PANEL - PADDING_Y/3);
	    
		//Desenha o eixo Y 
    	coluna = new Line2D.Double( PADDING_X, 0, PADDING_X, ALTURA_PANEL );
    	graphics.setPaint( Color.green );
    	graphics.setStroke(drawingStroke);
    	graphics.draw( coluna );
    	
    	//Desenha o eixo X
    	linha = new Line2D.Double( 0, ALTURA_PANEL - PADDING_Y, LARGURA_PANEL, ALTURA_PANEL - PADDING_Y );
    	graphics.setPaint( Color.red );
    	graphics.setStroke(drawingStroke);
    	graphics.draw( linha );
    	
    	int fitnessSize = fitnessList.size();
    	
    	double fitnessAtual,
    		proximoFitness;
    	
    	drawingStroke = new BasicStroke(2);
    	
    	for (int j = 0; j < fitnessSize-1; j++) {
    		fitnessAtual = fitnessList.get(j);
    		proximoFitness = fitnessList.get(j+1);
    		
    		linha = new Line2D.Double(getXGrafico(j), getYGrafico(fitnessAtual), getXGrafico(j+1), getYGrafico(proximoFitness));
			graphics.setPaint( Color.orange );
			graphics.setStroke(drawingStroke);
			graphics.draw( linha );	
		}
	}
	
	public void adicionarFitness(double fitness){
		if(melhorFitness < fitness){
			melhorFitness = fitness;
		}
		fitnessList.add(fitness);
		repaint();
	}

	
	private double getXGrafico(double x){
		double retorno = 0,
		areaValida = (LARGURA_PANEL - PADDING_X) - (PADDING_X);
	
		//regra de tres
		retorno = areaValida * x / tempoMaximoGrafico;
		
		retorno += PADDING_X;
		
		return retorno;
	}
	
	private double getYGrafico(double y){
		double retorno = 0,
			areaValida = (ALTURA_PANEL - PADDING_Y) - (PADDING_Y);
		
		//regra de tres
		retorno = areaValida * y / fitnessMaximoGrafico;
		
		retorno = (ALTURA_PANEL - PADDING_Y) - retorno;
		
		return retorno;
	}
}