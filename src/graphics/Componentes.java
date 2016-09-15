package graphics;
/**
 * @author Jonathan
 * @turma SI
 */
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

public class Componentes {
	private Container 	content;
	private JFrame 		janela;

	private JMenu 		menu;
	private JMenu 		menuLoading;
    private JMenuBar 	menuBar;
    private JMenuItem	menuItemSair;
    
    private CanvasCoordenadas		canvas;
    private CanvasGrafico			canvasGrafico;
    
    private ArrayList<JLabel> 		labelPosicaoList;
    private ArrayList<JTextField> 	textFieldPosicaoXList;
    private ArrayList<JTextField> 	textFieldPosicaoYList;
    private JButton		botaoRedefinirPosicao;
    private JButton		botaoRandomizarPosicao;
    
    private JButton		buttonGerarPopuacaoInicial;
    private JButton		buttonNovaGeracao;
    private JButton		buttonDezNovasGeracoes;
    private JButton		buttonCemNovasGeracoes;
    private JButton		buttonMilNovasGeracoes;
    private JButton		buttonGerarIteracoesInifinitamente;
    private JButton		buttonDijkstra;
    
    private JLabel labelCaminho;
    private JLabel labelFitnessTotal;
    
    private ArrayList<ArrayList<JTextField>>	textFieldCaminhoList;
    private ArrayList<JTextField>	textFieldFitnessTotalList;
    
    private JTextField textFieldFitnessMedio;
    
    private ArrayList<JButton>	buttonDesenharList;
    
    private JLabel labelCrossOver;
    private JComboBox comboCrossOver;

    private JLabel labelMutacao;
    private JComboBox comboMutacao;
    
    private JLabel labelSalvacionismo;
    private JCheckBox checkSalvacionismo;
    
    private JLabel labelElitismo;
    private JCheckBox checkElitismo;
    
    
    public Componentes() {
		labelPosicaoList = new ArrayList<JLabel>();
		textFieldPosicaoXList = new ArrayList<JTextField>();
		textFieldPosicaoYList = new ArrayList<JTextField>();
		textFieldCaminhoList = new ArrayList<ArrayList<JTextField>>();
		textFieldFitnessTotalList = new ArrayList<JTextField>();
		buttonDesenharList = new ArrayList<JButton>();
	}
    
	public void startLoading(){
		getMenuLoading().setVisible(true);		
	}
	
	public void stopLoading(){
		getMenuLoading().setVisible(false);
	}

	public JMenu getMenuLoading() {
		return menuLoading;
	}

	public void setMenuLoading(JMenu menuLoading) {
		this.menuLoading = menuLoading;
	}

	public Container getContent() {
		return content;
	}

	public void setContent(Container content) {
		this.content = content;
	}

	public JFrame getJanela() {
		return janela;
	}

	public void setJanela(JFrame janela) {
		this.janela = janela;
	}

	public JMenu getMenu() {
		return menu;
	}

	public void setMenu(JMenu menu) {
		this.menu = menu;
	}

	public JMenuBar getMenuBar() {
		return menuBar;
	}

	public void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
	}

	public JMenuItem getMenuItemSair() {
		return menuItemSair;
	}

	public void setMenuItemSair(JMenuItem menuItemSair) {
		this.menuItemSair = menuItemSair;
	}

	public CanvasCoordenadas getCanvas() {
		return canvas;
	}

	public void setCanvas(CanvasCoordenadas canvas) {
		this.canvas = canvas;
	}

	public ArrayList<JLabel> getLabelPosicaoList() {
		return labelPosicaoList;
	}

	public void setLabelPosicaoList(ArrayList<JLabel> labelPosicaoList) {
		this.labelPosicaoList = labelPosicaoList;
	}

	public ArrayList<JTextField> getTextFieldPosicaoXList() {
		return textFieldPosicaoXList;
	}

	public void setTextFieldPosicaoXList(ArrayList<JTextField> textFieldPosicaoXList) {
		this.textFieldPosicaoXList = textFieldPosicaoXList;
	}

	public ArrayList<JTextField> getTextFieldPosicaoYList() {
		return textFieldPosicaoYList;
	}

	public void setTextFieldPosicaoYList(ArrayList<JTextField> textFieldPosicaoYList) {
		this.textFieldPosicaoYList = textFieldPosicaoYList;
	}

	public JButton getBotaoRedefinirPosicao() {
		return botaoRedefinirPosicao;
	}

	public void setBotaoRedefinirPosicao(JButton botaoRedefinirPosicao) {
		this.botaoRedefinirPosicao = botaoRedefinirPosicao;
	}

	public JButton getButtonNovaGeracao() {
		return buttonNovaGeracao;
	}

	public void setButtonNovaGeracao(JButton buttonNovaGeracao) {
		this.buttonNovaGeracao = buttonNovaGeracao;
	}

	public JLabel getLabelCaminho() {
		return labelCaminho;
	}

	public void setLabelCaminho(JLabel labelCaminho) {
		this.labelCaminho = labelCaminho;
	}

	public JButton getButtonGerarPopuacaoInicial() {
		return buttonGerarPopuacaoInicial;
	}

	public void setButtonGerarPopuacaoInicial(JButton buttonGerarPopuacaoInicial) {
		this.buttonGerarPopuacaoInicial = buttonGerarPopuacaoInicial;
	}

	public ArrayList<ArrayList<JTextField>> getTextFieldCaminhoList() {
		return textFieldCaminhoList;
	}

	public void setTextFieldCaminhoList(
			ArrayList<ArrayList<JTextField>> textFieldCaminhoList) {
		this.textFieldCaminhoList = textFieldCaminhoList;
	}

	public JLabel getLabelFitnessTotal() {
		return labelFitnessTotal;
	}

	public void setLabelFitnessTotal(JLabel labelFitnessTotal) {
		this.labelFitnessTotal = labelFitnessTotal;
	}

	public ArrayList<JTextField> getTextFieldFitnessTotalList() {
		return textFieldFitnessTotalList;
	}

	public void setTextFieldFitnessTotalList(
			ArrayList<JTextField> textFieldFitnessTotalList) {
		this.textFieldFitnessTotalList = textFieldFitnessTotalList;
	}

	public ArrayList<JButton> getButtonDesenharList() {
		return buttonDesenharList;
	}

	public void setButtonDesenharList(ArrayList<JButton> buttonDesenharList) {
		this.buttonDesenharList = buttonDesenharList;
	}

	public JButton getButtonDezNovasGeracoes() {
		return buttonDezNovasGeracoes;
	}

	public void setButtonDezNovasGeracoes(JButton buttonDezNovasGeracoes) {
		this.buttonDezNovasGeracoes = buttonDezNovasGeracoes;
	}

	public JButton getButtonCemNovasGeracoes() {
		return buttonCemNovasGeracoes;
	}

	public void setButtonCemNovasGeracoes(JButton buttonCemNovasGeracoes) {
		this.buttonCemNovasGeracoes = buttonCemNovasGeracoes;
	}

	public JButton getButtonMilNovasGeracoes() {
		return buttonMilNovasGeracoes;
	}

	public void setButtonMilNovasGeracoes(JButton buttonMilNovasGeracoes) {
		this.buttonMilNovasGeracoes = buttonMilNovasGeracoes;
	}

	public JButton getButtonDijkstra() {
		return buttonDijkstra;
	}

	public void setButtonDijkstra(JButton buttonDijkstra) {
		this.buttonDijkstra = buttonDijkstra;
	}

	public JTextField getTextFieldFitnessMedio() {
		return textFieldFitnessMedio;
	}

	public void setTextFieldFitnessMedio(JTextField textFieldFitnessMedio) {
		this.textFieldFitnessMedio = textFieldFitnessMedio;
	}

	public CanvasGrafico getCanvasGrafico() {
		return canvasGrafico;
	}

	public void setCanvasGrafico(CanvasGrafico canvasGrafico) {
		this.canvasGrafico = canvasGrafico;
	}

	public JLabel getLabelCrossOver() {
		return labelCrossOver;
	}

	public void setLabelCrossOver(JLabel labelCrossOver) {
		this.labelCrossOver = labelCrossOver;
	}

	public JComboBox getComboCrossOver() {
		return comboCrossOver;
	}

	public void setComboCrossOver(JComboBox comboCrossOver) {
		this.comboCrossOver = comboCrossOver;
	}

	public JLabel getLabelMutacao() {
		return labelMutacao;
	}

	public void setLabelMutacao(JLabel labelMutacao) {
		this.labelMutacao = labelMutacao;
	}

	public JComboBox getComboMutacao() {
		return comboMutacao;
	}

	public void setComboMutacao(JComboBox comboMutacao) {
		this.comboMutacao = comboMutacao;
	}

	public JLabel getLabelSalvacionismo() {
		return labelSalvacionismo;
	}

	public void setLabelSalvacionismo(JLabel labelSalvacionismo) {
		this.labelSalvacionismo = labelSalvacionismo;
	}

	public JCheckBox getCheckSalvacionismo() {
		return checkSalvacionismo;
	}

	public void setCheckSalvacionismo(JCheckBox checkSalvacionismo) {
		this.checkSalvacionismo = checkSalvacionismo;
	}

	public JLabel getLabelElitismo() {
		return labelElitismo;
	}

	public void setLabelElitismo(JLabel labelElitismo) {
		this.labelElitismo = labelElitismo;
	}

	public JCheckBox getCheckElitismo() {
		return checkElitismo;
	}

	public void setCheckElitismo(JCheckBox checkElitismo) {
		this.checkElitismo = checkElitismo;
	}

	public JButton getButtonGerarIteracoesInifinitamente() {
		return buttonGerarIteracoesInifinitamente;
	}

	public void setButtonGerarIteracoesInifinitamente(
			JButton buttonGerarIteracoesInifinitamente) {
		this.buttonGerarIteracoesInifinitamente = buttonGerarIteracoesInifinitamente;
	}

	public JButton getBotaoRandomizarPosicao() {
		return botaoRandomizarPosicao;
	}

	public void setBotaoRandomizarPosicao(JButton botaoRandomizarPosicao) {
		this.botaoRandomizarPosicao = botaoRandomizarPosicao;
	}

}