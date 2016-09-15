package graphics;
/**
 * @author Jonathan
 * @turma SI
 */
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import listener.ActionsListener;
import listener.ExitListener;
import algoritmos.CE;

public class Formulario {
	private Componentes componentes;
	private ActionsListener action;
	
	/** LAYOUT */
	private final int PADDING_X 	= 10,
				PADDING_Y 	= 10,
				LINE_HEIGHT = 20,
				NUMERO_DE_CIDADES = 12;
	
	public Formulario() {
		try{
			componentes 	= new Componentes();
			action 			= new ActionsListener(componentes);

			inicializaJanela();
			inicializaComponentes();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(){
		componentes.getJanela().setVisible(true);
	}
	
	private void inicializaComponentes(){
		inicializaMenu();
		inicializaCanvasCoordenada();
		inicializaCanvasGrafico();
		inicializaOpcoes();
		inicializaOpcoesCE();
	}
	
	private void inicializaJanela(){
		componentes.setJanela(new JFrame("Computação Evolutiva"));
		componentes.getJanela().setSize(880, 670);
		componentes.getJanela().setLocationRelativeTo(null);
	    
		componentes.setContent(componentes.getJanela().getContentPane());
		componentes.getContent().setBackground(new Color(234, 234, 234));
	    
		componentes.getJanela().addWindowListener(new ExitListener());   
		
		componentes.getJanela().setLayout(null);
		componentes.getJanela().setResizable(false);
		componentes.getJanela().setVisible(false);
	}
	
	private void inicializaMenu(){
		componentes.setMenuBar(new JMenuBar());
		componentes.setMenu(new JMenu());
		componentes.setMenuLoading(new JMenu());
		componentes.setMenuItemSair( new JMenuItem() );
		
		componentes.getMenu().setText("Menu");
		componentes.getMenu().setMnemonic(componentes.getMenu().getText().charAt(0));
		componentes.getMenu().setVisible(true);
		
		componentes.getMenuItemSair().setText("Sair");
		componentes.getMenuItemSair().setMnemonic(componentes.getMenuItemSair().getText().charAt(0));
		componentes.getMenuItemSair().addActionListener(action);
		componentes.getMenuItemSair().setVisible(true);

		componentes.getMenuLoading().setText("");
		componentes.getMenuLoading().setIcon(new ImageIcon("resources/images/loading.gif"));
		componentes.getMenuLoading().setVisible(false);
				
		componentes.getMenu().add(componentes.getMenuItemSair());		
		componentes.getMenuBar().add(componentes.getMenu());
		componentes.getMenuBar().add(componentes.getMenuLoading());
		componentes.getJanela().setJMenuBar(componentes.getMenuBar());
	}

	private void inicializaCanvasCoordenada(){

		componentes.setCanvas( new CanvasCoordenadas() );
		
		componentes.getCanvas().setBounds( new Rectangle(PADDING_X, 230, 450, 380) );
		
		componentes.getJanela().add( componentes.getCanvas() );

		CE.inicializarPosicaoCidadeList();
		
		desenhaTodasCidades();
	}

	private void inicializaCanvasGrafico(){

		componentes.setCanvasGrafico( new CanvasGrafico() );
		
		componentes.getCanvasGrafico().setBounds( new Rectangle(PADDING_X + 470, 230, 380, 380) );
		
		componentes.getJanela().add( componentes.getCanvasGrafico() );

	}

	
	public void desenhaTodasCidades(){
		for ( Point2D ponto: CE.posicaoCidadesList ) {
			componentes.getCanvas().desenharCidade((int)ponto.getX(), (int)ponto.getY());
		}
	}
	
	private void inicializaOpcoes(){
		JLabel labelPosicao;
		JTextField textFieldPosicaoX,
			textFieldPosicaoY;
		
		int y = 30,
			x = 0,
			i;
		
		Point2D posicaoCidade;
		for (i = 0; i < NUMERO_DE_CIDADES; i++) {
			x = 580;
			if(i >= 6){
				x = 710;	
			}
			if(i == 6){
				y = 30;
			}
			posicaoCidade = CE.posicaoCidadesList.get(i);
			 
			labelPosicao = new JLabel("Cidade "+(i+1));
			labelPosicao.setBounds(x, PADDING_Y + y, 80, LINE_HEIGHT);
			
			x += 60;
			
			textFieldPosicaoX = new JTextField();
			textFieldPosicaoX.setBounds(x, PADDING_Y + y, 30, LINE_HEIGHT);
			textFieldPosicaoX.setText(String.valueOf(posicaoCidade.getX()));
			
			x+= 30;
			
			textFieldPosicaoY = new JTextField();
			textFieldPosicaoY.setBounds(x, PADDING_Y + y, 30, LINE_HEIGHT);
			textFieldPosicaoY.setText(String.valueOf(posicaoCidade.getY()));
			
			componentes.getLabelPosicaoList().add(labelPosicao);
			componentes.getTextFieldPosicaoXList().add(textFieldPosicaoX);
			componentes.getTextFieldPosicaoYList().add(textFieldPosicaoY);
			
			componentes.getJanela().add(labelPosicao);
			componentes.getJanela().add(textFieldPosicaoX);
			componentes.getJanela().add(textFieldPosicaoY);
			
			y += 20;
		}
		x = 560;
		
		componentes.setBotaoRedefinirPosicao(new JButton("Redefinir Posições"));
		componentes.getBotaoRedefinirPosicao().setBounds(x, PADDING_Y + y + 5 , 300, LINE_HEIGHT);
		componentes.getBotaoRedefinirPosicao().addActionListener( action );
		componentes.getJanela().add(componentes.getBotaoRedefinirPosicao());
		
		y+= 20;
		
		componentes.setBotaoRandomizarPosicao(new JButton("Randomizar Posições"));
		componentes.getBotaoRandomizarPosicao().setBounds(x, PADDING_Y + y + 5 , 300, LINE_HEIGHT);
		componentes.getBotaoRandomizarPosicao().addActionListener( action );
		componentes.getJanela().add(componentes.getBotaoRandomizarPosicao());
	}
	
	private void inicializaOpcoesCE(){
		int x = PADDING_X,
			y = PADDING_Y;
		
		y = 100;
		
		componentes.setLabelCaminho( new JLabel("Caminhos:"));
		componentes.getLabelCaminho().setBounds(x, y, 80, LINE_HEIGHT);
		
		componentes.getJanela().add(componentes.getLabelCaminho());
		
		componentes.setLabelFitnessTotal( new JLabel("Distância"));
		componentes.getLabelFitnessTotal().setBounds(380, y, 100, LINE_HEIGHT);
		
		componentes.getJanela().add(componentes.getLabelFitnessTotal());
		
		y += 20;
		for (int i = 0; i < CE.TAMANHO; i++) {
			componentes.getTextFieldCaminhoList().add(new ArrayList<JTextField>());
			x = PADDING_X;
			for (int j = 0; j < componentes.getTextFieldPosicaoXList().size(); j++) {
				JTextField texto = new JTextField();
				texto.setBounds(x, y, 30, LINE_HEIGHT);
				
				componentes.getTextFieldCaminhoList().get(i).add(texto);
				
				componentes.getJanela().add(texto);
				
				x += 30;
			}
			
			JTextField texto = new JTextField();
			texto.setBounds(x, y, 80, LINE_HEIGHT);
			componentes.getJanela().add(texto);
			
			componentes.getTextFieldFitnessTotalList().add(texto);
			
			x += 80;
			
			JButton botao = new JButton("Desenhar");
			botao.setBounds(x, y, 100, LINE_HEIGHT);
			botao.addActionListener(action);
			componentes.getJanela().add(botao);
			
			componentes.getButtonDesenharList().add(botao);
			y += 20;
		}

		JTextField texto = new JTextField();
		texto.setBounds(x-80, y, 80, LINE_HEIGHT);
		componentes.getJanela().add(texto);
		componentes.setTextFieldFitnessMedio(texto);
		
		x = PADDING_X;
		y = PADDING_Y;
		
		componentes.setButtonGerarPopuacaoInicial( new JButton("Gerar Populção Inicial") );
		componentes.getButtonGerarPopuacaoInicial().setBounds(x, y, 280, (int) (LINE_HEIGHT * 1.0f));
		componentes.getButtonGerarPopuacaoInicial().addActionListener(action);

		x += 300;
		
		componentes.setLabelCrossOver( new JLabel("Cross Over") );
		componentes.getLabelCrossOver().setBounds(x, y, 100, (int) (LINE_HEIGHT * 1.0f));		
		
		x += 100;
		
		componentes.setComboCrossOver( new JComboBox(new String[]{"PMX", "OX", "CX"}) );
		componentes.getComboCrossOver().setBounds(x, y, 100, (int) (LINE_HEIGHT * 1.0f));
		
		x = PADDING_X;
		y += LINE_HEIGHT * 1.0f;
		
		componentes.setButtonNovaGeracao( new JButton("Nova Geração") );
		componentes.getButtonNovaGeracao().setBounds(x, y, 280, (int) (LINE_HEIGHT * 1.0f));
		componentes.getButtonNovaGeracao().addActionListener(action);

		x += 300;
		
		componentes.setLabelMutacao( new JLabel("Mutação") );
		componentes.getLabelMutacao().setBounds(x, y, 100, (int) (LINE_HEIGHT * 1.0f));

		x += 100;
		
		componentes.setComboMutacao( new JComboBox(new String[]{"Reciprocal Exchange", "Inversion"}) );
		componentes.getComboMutacao().setBounds(x, y, 150, (int) (LINE_HEIGHT * 1.0f));
		
		x = PADDING_X;
		y += LINE_HEIGHT * 1.0f;
		
		componentes.setButtonCemNovasGeracoes( new JButton("100 Novas Gerações") );
		componentes.getButtonCemNovasGeracoes().setBounds(x, y, 280, (int) (LINE_HEIGHT * 1.0f));
		componentes.getButtonCemNovasGeracoes().addActionListener(action);
		
		x += 300;
		
		componentes.setLabelSalvacionismo( new JLabel("Salvacionismo"));
		componentes.getLabelSalvacionismo().setBounds(x, y, 100, (int) (LINE_HEIGHT * 1.0f));

		x += 100;
		
		componentes.setCheckSalvacionismo( new JCheckBox() );
		componentes.getCheckSalvacionismo().setBounds(x, y, 20, (int) (LINE_HEIGHT * 1.0f));
		componentes.getCheckSalvacionismo().setSelected(true);
		
		x = PADDING_X;
		y += LINE_HEIGHT * 1.0f;
		
		componentes.setButtonGerarIteracoesInifinitamente( new JButton("Gerar novas Gerações") );
		componentes.getButtonGerarIteracoesInifinitamente().setBounds(x, y, 280, (int) (LINE_HEIGHT * 1.0f));
		componentes.getButtonGerarIteracoesInifinitamente().addActionListener(action);
		
		
		x += 300;
		
		componentes.setLabelElitismo( new JLabel("Elitismo"));
		componentes.getLabelElitismo().setBounds(x, y, 100, (int) (LINE_HEIGHT * 1.0f));		

		x += 100;
		
		componentes.setCheckElitismo( new JCheckBox() );
		componentes.getCheckElitismo().setBounds(x, y, 20, (int) (LINE_HEIGHT * 1.0f));
		componentes.getCheckElitismo().setSelected(true);
		
		x = PADDING_X;
		y += LINE_HEIGHT * 1.0f;

		/*
		componentes.setButtonMilNovasGeracoes( new JButton("1000 Novas Gerações") );
		componentes.getButtonMilNovasGeracoes().setBounds(x, y, 280, (int) (LINE_HEIGHT * 1.0f));
		componentes.getButtonMilNovasGeracoes().addActionListener(action);
		*/
		
		componentes.getJanela().add( componentes.getButtonGerarPopuacaoInicial() );
		componentes.getJanela().add( componentes.getButtonNovaGeracao() );
		componentes.getJanela().add( componentes.getButtonCemNovasGeracoes() );
		componentes.getJanela().add( componentes.getButtonGerarIteracoesInifinitamente() );
		componentes.getJanela().add( componentes.getLabelCrossOver() );
		componentes.getJanela().add( componentes.getComboCrossOver() );
		componentes.getJanela().add( componentes.getLabelMutacao() );
		componentes.getJanela().add( componentes.getComboMutacao() );
		componentes.getJanela().add( componentes.getLabelSalvacionismo() );
		componentes.getJanela().add( componentes.getCheckSalvacionismo() );
		componentes.getJanela().add( componentes.getLabelElitismo() );
		componentes.getJanela().add( componentes.getCheckElitismo() );
		/*
		componentes.getJanela().add( componentes.getButtonMilNovasGeracoes() );
		*/
	}
}