package complier;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

import analyser.Analyser;
import analyser.FourItem;
import analyser.Generator;
import parser.Parser;
import tokenizer.Token;
import tokenizer.Tokenizer;
import error.Error;
import interpreter.Interpreter;

// ����������� �������͵���������
public class Compiler extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	
	// �˵�
	private JMenuItem openMenu;
	private JMenuItem saveMenu;
	private JMenuItem tokenizerMenu;
	private JMenuItem parserMenu;
	private JMenuItem analyserMenu;
	private JMenuItem runMenu;
	private JMenuItem compileAndRunMenu;
	
	// �ı�
	private JTextArea textArea;
	private JTable tokenTable;
	private DefaultTableModel tokenTableModel;
	private JTextArea codeArea;
	private JTextArea resultArea;
	
	// ����
	private Tokenizer tokenizer;
	private Parser parser;
	private Analyser analyser;
	private Generator generator;
	private Interpreter interpreter;
	
	public Compiler () {
		Font textFont = new Font("����", 0, 18);
		
		openMenu = new JMenuItem("��");
		saveMenu = new JMenuItem("����");
		tokenizerMenu = new JMenuItem("�ʷ�����");
		parserMenu = new JMenuItem("�﷨����");
		analyserMenu = new JMenuItem("�������");
		runMenu = new JMenuItem("ִ��");
		compileAndRunMenu = new JMenuItem("���벢����");
		
		textArea = new JTextArea(22, 40);
		tokenTableModel = new DefaultTableModel(null, new String[] {"ֵ", "���"});
		tokenTable = new JTable(tokenTableModel);
		tokenTable.setRowHeight(23);
		tokenTable.getTableHeader().setFont(textFont);
		codeArea = new JTextArea(32, 40);
		resultArea = new JTextArea(9, 80);
		
		tokenizer = new Tokenizer();	
		parser = new Parser(new File("grammar.txt"));
		analyser = new Analyser();
		generator = new Generator();
		interpreter = new Interpreter(resultArea);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("�ļ�");
		JMenu compile = new JMenu("����");
		JMenu run = new JMenu("����");
		
		openMenu.addActionListener(this);
		saveMenu.addActionListener(this);
		tokenizerMenu.addActionListener(this);
		parserMenu.addActionListener(this);
		analyserMenu.addActionListener(this);
		runMenu.addActionListener(this);
		compileAndRunMenu.addActionListener(this);
		
		file.add(openMenu);
		file.add(saveMenu);
		compile.add(tokenizerMenu);
		compile.add(parserMenu);
		compile.add(analyserMenu);
		run.add(runMenu);
		run.add(compileAndRunMenu);
		menuBar.add(file);
		menuBar.add(compile);
		menuBar.add(run);
		
		JPanel textPanel = new JPanel();
		JPanel tokensPanel = new JPanel();
		JPanel codePanel = new JPanel();
		JPanel resultPanel = new JPanel();
		
		JScrollPane textScroll = new JScrollPane(textArea);
		textArea.setTabSize(2);
		textArea.setLineWrap(true);
		textArea.setFont(textFont);
		JLabel textLabel = new JLabel("�ı��༭");
		textPanel.setLayout(new BorderLayout());
		textPanel.add(textLabel, BorderLayout.NORTH);
		textPanel.add(textScroll);
		
		JScrollPane tokensScroll = new JScrollPane(tokenTable);
		tokenTable.setEnabled(false);
		tokenTable.setPreferredScrollableViewportSize(new Dimension(340, 460));
		tokenTable.setFont(textFont);
		JLabel tokensLabel = new JLabel("�ʷ�����");
		tokensPanel.setLayout(new BorderLayout());
		tokensPanel.add(tokensLabel, BorderLayout.NORTH);
		tokensPanel.add(tokensScroll);
		
		JScrollPane codeScroll = new JScrollPane(codeArea);
		codeArea.setLineWrap(true);
		codeArea.setEditable(false);
		codeArea.setFont(textFont);
		JLabel codeLabel = new JLabel("�м����");
		codePanel.setLayout(new BorderLayout());
		codePanel.add(codeLabel, BorderLayout.NORTH);
		codePanel.add(codeScroll);
		
		JScrollPane resultScroll = new JScrollPane(resultArea);
		resultArea.setLineWrap(true);
		resultArea.setFont(textFont);
		resultPanel.add(resultScroll);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screen = toolkit.getScreenSize();
		this.setSize(1220, 870);
		this.setLocation(screen.width / 2 - this.getWidth() / 2, screen.height / 2 - this.getHeight() / 2);
		this.setResizable(false);
		this.setJMenuBar(menuBar);
		this.setLayout(new GridBagLayout());
		this.add(textPanel, new GBC(0, 0, 1, 3).setInsets(8));
		this.add(tokensPanel, new GBC(1, 0, 1, 3).setInsets(8));
		this.add(codePanel, new GBC(2, 0, 1, 4).setInsets(8));
		this.add(resultPanel, new GBC(0, 3, 2, 1));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tokenizerMenu) {
			// �ʷ�����
			tokenTableModel.setRowCount(0);
			resultArea.setText("");
			tokenizer.analysis(textArea.getText());
			List<Token> tokens = tokenizer.getTokens();
			for (int i = 0; i < tokens.size() - 1; i++) {
				Token token = tokens.get(i);
				tokenTableModel.addRow(new String[] {token.getValue(), token.getType().print()});
			}
			
			List<Error> errors = tokenizer.getErrors();
			for (Error error : errors) {
				resultArea.append(error + "\n");
			}
 		} else if (e.getSource() == parserMenu) {
 			// �﷨����
 			if (tokenizer.getTokens() == null || tokenizer.getTokens().size() == 1) {
 				resultArea.append("δ�����ַ���δ���дʷ�����\n");
 				return ;
 			} else if (!resultArea.getText().isBlank() && !resultArea.getText().contains("���﷨����")) {
 				resultArea.append("���ڴ��� �޷������﷨����\n");
 				return ;
 			}
 			parser.parse(tokenizer);
 			List<Error> errors = parser.getErrors();
			for (Error error : errors) {
				resultArea.append(error + "\n");
			}
			if (errors.size() == 0) {
				resultArea.append("�﷨������ ���﷨����\n");
			}
 		} else if (e.getSource() == analyserMenu) {
 			// ����������м��������
 			if (parser.getRoot() == null) {
 				resultArea.append("δ�����﷨����\n");
 				return ;
 			}
 			String text = resultArea.getText();
 			if (!text.isBlank() && !text.contains("���﷨����")) {
 				resultArea.append("���ڴ��� �޷������������\n");
 					return ;
 			}
 			
 			resultArea.setText("");
 			analyser.analyse(parser.getRoot());
 			codeArea.setText("");
 			List<Error> errors = analyser.getErrors();
			for (Error error : errors) {
				resultArea.append(error + "\n");
			}
			if (errors.size() == 0) {
				generator.generate(analyser.getFuncsInfoMap(), analyser.getRoot());
				List<FourItem> fourItems = generator.getFourItemList();
	 			for (int i = 0; i < fourItems.size(); i++) {
	 				FourItem item = fourItems.get(i);
	 				codeArea.append(String.format("%-3d:", i+1));
	 				codeArea.append(item + "\n");
	 			}
			}
 		} else if (e.getSource() == runMenu) {
 			if (generator.getFourItemList() == null) {
 				resultArea.append("δ�����������\n");
 				return ;
 			}
 			String text = resultArea.getText();
 			if (!text.isBlank() && !text.contains("���н���")) {
 				resultArea.append("���ڴ��� �޷�ִ��\n");
 					return ;
 			}
 			resultArea.setText("");
 			resultArea.requestFocus();
 			new Thread() {
				@Override
				public void run() {
					interpreter.interpreter(analyser.getFuncsInfoMap(), generator.getFourItemList());
				}
 			}.start();
 		} else if (e.getSource() == compileAndRunMenu) {
 			tokenTableModel.setRowCount(0);
 			codeArea.setText("");
			resultArea.setText("");
			
			tokenizer.analysis(textArea.getText());
			List<Token> tokens = tokenizer.getTokens();
			for (int i = 0; i < tokens.size() - 1; i++) {
				Token token = tokens.get(i);
				tokenTableModel.addRow(new String[] {token.getValue(), token.getType().print()});
			}
			if (tokenizer.getErrors().size() > 0) {
				for (Error error : tokenizer.getErrors()) {
					resultArea.append(error + "\n");
				}
				return ;
			}
			
			parser.parse(tokenizer);
			if (parser.getErrors().size() > 0) {
				for (Error error : parser.getErrors()) {
					resultArea.append(error + "\n");
				}
				return ;
			}
			
			analyser.analyse(parser.getRoot());
			if (analyser.getErrors().size() > 0) {
				for (Error error : analyser.getErrors()) {
					resultArea.append(error + "\n");
				}
				return ;
			}
			
			generator.generate(analyser.getFuncsInfoMap(), analyser.getRoot());
			List<FourItem> fourItems = generator.getFourItemList();
 			for (int i = 0; i < fourItems.size(); i++) {
 				FourItem item = fourItems.get(i);
 				codeArea.append(String.format("%-3d:", i+1));
 				codeArea.append(item + "\n");
 			}
			
			resultArea.requestFocus();
 			new Thread() {
				@Override
				public void run() {
					interpreter.interpreter(analyser.getFuncsInfoMap(), fourItems);
				}
 			}.start();
 		} else if (e.getSource() == openMenu) {
 			// ���ļ�
 			JFileChooser chooser = new JFileChooser();
 			chooser.setCurrentDirectory(new File("."));
 			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
 			chooser.showOpenDialog(this);
 			File file = new File(chooser.getSelectedFile().getPath());
 			textArea.setText("");
 			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
 				String line;
 				while ((line = reader.readLine()) != null) {
 					textArea.append(line + "\n");
 				}
 			} catch (IOException e2) {
				e2.printStackTrace();
			}
 		} else if (e.getSource() == saveMenu) {
 			// �����ļ�
 			JFileChooser chooser = new JFileChooser();
 			chooser.setCurrentDirectory(new File("."));
 			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
 			chooser.showOpenDialog(this);
 			File file = new File(chooser.getSelectedFile().getPath());
 			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
 				writer.write(textArea.getText());
 			} catch (IOException e1) {
				e1.printStackTrace();
			} 
 		}
	}
	
	public static void main(String[] args) {		
		Compiler frame = new Compiler();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
