package listener;
/**
 * @author Jonathan
 * @turma SI
 */
import graphics.Componentes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JTextField;

import thread.GerarGeracoes;
import algoritmos.CE;

public class ActionsListener implements ActionListener{
	
	private Thread threadGeracoes = null;
	private GerarGeracoes gerarGeracoes = null;
	private Componentes componentes;
	
	public ActionsListener(Componentes componentes){
		this.componentes = componentes;
	}
	
	@Override
	public void actionPerformed(final ActionEvent e) {
		new Thread(
			new Runnable() {
				public void run() {
					if(e.getSource() == componentes.getMenuItemSair()){
						System.exit(0);
					}else if(e.getSource() == componentes.getBotaoRedefinirPosicao()){
						redefinirPosicaoTodasCidades();
					}else if(e.getSource() == componentes.getBotaoRandomizarPosicao()){
						randomizarPosicaoTodasCidades();
					}else if(e.getSource() == componentes.getButtonGerarPopuacaoInicial()){
						gerarPopulacaoInicial();
					}else if(e.getSource() == componentes.getButtonNovaGeracao()){
						gerarNovaGeracao();
					}else if(e.getSource() == componentes.getButtonDezNovasGeracoes()){
						gerarDezGeracoes();
					}else if(e.getSource() == componentes.getButtonCemNovasGeracoes()){
						gerarCemGeracoes();
					}else if(e.getSource() == componentes.getButtonMilNovasGeracoes()){
						gerarMilGeracoes();
					}else if(e.getSource() == componentes.getButtonGerarIteracoesInifinitamente()){
						pararGerarIteracoes();
					}else{
						for (int i = 0; i < CE.TAMANHO; i++) {
							if(e.getSource() == componentes.getButtonDesenharList().get(i)){
								desenharCaminho(i);
							}
							
						}
					}
				}
			}
		).start();
	}

	private void randomizarPosicaoTodasCidades(){
		componentes.startLoading();
		
		JTextField posicaoX, posicaoY;
		
		int MIN_X = -10;
		int MIN_Y = -8;
		
		int MAX_X = 10;
		int MAX_Y = 8;
		
		for( int i = 0; i < componentes.getTextFieldPosicaoXList().size(); i++ ){
			posicaoX = componentes.getTextFieldPosicaoXList().get(i);
			posicaoY = componentes.getTextFieldPosicaoYList().get(i);
			
			posicaoX.setText( String.valueOf((int) MIN_X + (int)(Math.random() * ((MAX_X - MIN_X) + 1))) );
			posicaoY.setText( String.valueOf((int) MIN_Y + (int)(Math.random() * ((MAX_Y - MIN_Y) + 1))) );
		}
		
		redefinirPosicaoTodasCidades();
		
		componentes.getCanvas().repaint();
		
		componentes.stopLoading();
	}
	
	private void redefinirPosicaoTodasCidades(){
		componentes.startLoading();
		
		ArrayList<Point2D> posicaoCidadesList = new ArrayList<Point2D>();
		
		componentes.getCanvas().limparCidades();
		componentes.getCanvas().limparCaminhos();
		
		JTextField posicaoX, posicaoY;
		
		for( int i = 0; i < componentes.getTextFieldPosicaoXList().size(); i++ ){
			posicaoX = componentes.getTextFieldPosicaoXList().get(i);
			posicaoY = componentes.getTextFieldPosicaoYList().get(i);
			
			Point2D ponto = new Point2D.Double( Double.parseDouble( posicaoX.getText()), Double.parseDouble( posicaoY.getText() ) );
			posicaoCidadesList.add(ponto);
		}
		
		CE.posicaoCidadesList = posicaoCidadesList;
		
		for ( Point2D ponto: CE.posicaoCidadesList ) {
			componentes.getCanvas().desenharCidade((int)ponto.getX(), (int)ponto.getY());
		}
		
		componentes.getCanvas().repaint();
		
		componentes.stopLoading();
		
	}
	
	private void gerarPopulacaoInicial() {
		componentes.getCanvas().limparCaminhos();
		componentes.getCanvasGrafico().limparAndamentoGrafico();
		
		CE.geraPopulacaoInicial();
		
		for (int i = 0; i < CE.TAMANHO; i++) {
			ArrayList<JTextField> textos = componentes.getTextFieldCaminhoList().get(i);
			for (int j = 0; j < CE.CAMINHOS; j++) {
				JTextField texto = textos.get(j);
				texto.setText(String.valueOf(CE.populacao[i][j]));
			}
		}
		
		for (int i = 0; i < CE.TAMANHO; i++) {
			JTextField texto = componentes.getTextFieldFitnessTotalList().get(i);
			texto.setText( String.valueOf( CE.fitness[i] ).substring(0, 10) );
		}
		desenharMelhorCaminho();
		componentes.getCanvasGrafico().adicionarFitness(getMelhorFitness());
		
		componentes.getTextFieldFitnessMedio().setText(String.valueOf( CE.fitnessMedio ).substring(0, 10));
	}
	
	public void gerarNovaGeracao(){
		CE.executar( componentes.getComboCrossOver().getSelectedItem().toString(), componentes.getComboMutacao().getSelectedItem().toString(), componentes.getCheckSalvacionismo().isSelected(), componentes.getCheckElitismo().isSelected());
		for (int i = 0; i < CE.TAMANHO; i++) {
			ArrayList<JTextField> textos = componentes.getTextFieldCaminhoList().get(i);
			for (int j = 0; j < CE.CAMINHOS; j++) {
				JTextField texto = textos.get(j);
				texto.setText(String.valueOf(CE.populacao[i][j]));
			}
		}
		
		for (int i = 0; i < CE.TAMANHO; i++) {
			JTextField texto = componentes.getTextFieldFitnessTotalList().get(i);
			texto.setText( String.valueOf( CE.fitness[i] ).substring(0, 10) );
		}

		desenharMelhorCaminho();
		componentes.getCanvasGrafico().adicionarFitness( getMelhorFitness() );
		
		componentes.getTextFieldFitnessMedio().setText(String.valueOf( CE.fitnessMedio ).substring(0, 10));
	}
	
	private void gerarDezGeracoes(){
		componentes.startLoading();
		for (int i = 0; i < 10; i++) {
			gerarNovaGeracao();
		}
		componentes.stopLoading();
	}
	
	private void gerarCemGeracoes(){
		componentes.startLoading();
		for (int i = 0; i < 100; i++) {
			gerarNovaGeracao();
		}		
		componentes.stopLoading();
	}

	private void gerarMilGeracoes(){
		componentes.startLoading();
		for (int i = 0; i < 1000; i++) {
			gerarNovaGeracao();
		}
		componentes.stopLoading();	
	}
	
	private void pararGerarIteracoes(){
		componentes.startLoading();
		
		if( threadGeracoes == null ){
			gerarGeracoes = new GerarGeracoes(this);
			threadGeracoes = new Thread( gerarGeracoes );
			
			componentes.getButtonGerarIteracoesInifinitamente().setText("Parar Geração");
			
			threadGeracoes.start();
		}else{
			try {
				gerarGeracoes.stop();
				threadGeracoes.join();
				componentes.getButtonGerarIteracoesInifinitamente().setText("Gerar novas Gerações");
				threadGeracoes = null;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
		componentes.stopLoading();
	}
	
	private void desenharCaminho(int caminho){
		componentes.getCanvas().limparCaminhos();
		ArrayList<JTextField> textos = componentes.getTextFieldCaminhoList().get(caminho);
		for (int j = 0; j < CE.CAMINHOS; j++) {
			Point2D cidadeA, cidadeB;
			
			cidadeA = CE.posicaoCidadesList.get( Integer.parseInt( textos.get(j).getText() ) - 1 );
			cidadeB = CE.posicaoCidadesList.get( Integer.parseInt( textos.get((j+1) % CE.CAMINHOS ).getText() ) -1 );
			
			componentes.getCanvas().desenharCaminho(
					(int) cidadeA.getX(), (int) cidadeA.getY(), 
					(int) cidadeB.getX(), (int) cidadeB.getY());
			
		}			
		componentes.getCanvas().repaint();
	}
	
	private void desenharMelhorCaminho(){
		double melhorFitness = Double.POSITIVE_INFINITY;
		int pos = 0;
		for (int i = 0; i < CE.TAMANHO; i++) {
			if(CE.fitness[i] < melhorFitness){
				melhorFitness = CE.fitness[i];
				pos = i;
			}
		}
		desenharCaminho(pos);
	}
	
	private double getMelhorFitness(){
		double melhorFitness = Double.POSITIVE_INFINITY;
		for (int i = 0; i < CE.TAMANHO; i++) {
			if(CE.fitness[i] < melhorFitness){
				melhorFitness = CE.fitness[i];
			}
		}
		return melhorFitness;
	}
}