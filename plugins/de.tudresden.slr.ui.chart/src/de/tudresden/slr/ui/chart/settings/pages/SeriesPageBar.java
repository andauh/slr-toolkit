package de.tudresden.slr.ui.chart.settings.pages;

import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

import de.tudresden.slr.model.taxonomy.Term;
import de.tudresden.slr.ui.chart.logic.BarDataTerm;
import de.tudresden.slr.ui.chart.logic.ChartDataProvider;
import de.tudresden.slr.ui.chart.logic.TermSort;
import de.tudresden.slr.ui.chart.settings.BarChartConfiguration;
import de.tudresden.slr.ui.chart.settings.SettingsDialog;
import de.tudresden.slr.ui.chart.settings.TreeDialogBar;

public class SeriesPageBar extends Composite implements SelectionListener, MouseListener, Pages{

	private Button btnRadioButtonGrey, btnRadioButtonCustom, btnRadioButtonRandom, btnNewButton, btnCites;
	private Button btnCheckButton;
	private List list;
	private java.util.List<BarDataTerm> barTermList= new ArrayList<>();
	public Term selectedTerm;
	public TermSort termSort = TermSort.YEAR;
	private ChartDataProvider chartDataProvider = new ChartDataProvider();
	
	private Label labelShowColor;
	private Composite compositeFirst;
	private Label lblSelectedTermIs;
	
	private BarChartConfiguration settings = BarChartConfiguration.get();	
	private Button btnOneColor;
	private Label lblColor;
	
	public SeriesPageBar(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		
		compositeFirst = new Composite(this, SWT.NONE);
		compositeFirst.setLayout(new GridLayout(2, false));
		compositeFirst.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		
		btnNewButton = new Button(compositeFirst, SWT.NONE);
		btnNewButton.setText("Select Term");
		btnCites = new Button(compositeFirst, SWT.NONE);
		btnCites.setText("Cites");
		
		lblSelectedTermIs = new Label(compositeFirst, SWT.CENTER);
		GridData gd_lblSelectedTermIs = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_lblSelectedTermIs.widthHint = 367;
		lblSelectedTermIs.setLayoutData(gd_lblSelectedTermIs);
		lblSelectedTermIs.setText("No Term Selected");
		btnNewButton.addSelectionListener(this);
		btnCites.addSelectionListener(this);
		
		
		
		Composite compositeCentre = new Composite(this, SWT.NONE);
		compositeCentre.setLayout(new GridLayout(1, false));
		compositeCentre.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
		
		list = new List(compositeCentre, SWT.BORDER | SWT.V_SCROLL);
		GridData gd_list = new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1);
		gd_list.widthHint = 400;
		list.setLayoutData(gd_list);
		list.setBounds(0, 0, 71, 68);
		list.addSelectionListener(this);
		
		Composite compositeNorth = new Composite(this, SWT.NONE);
		compositeNorth.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		FillLayout fl_compositeNorth = new FillLayout(SWT.HORIZONTAL);
		fl_compositeNorth.marginWidth = 5;
		fl_compositeNorth.spacing = 5;
		compositeNorth.setLayout(fl_compositeNorth);
		
		lblColor = new Label(compositeNorth, SWT.NONE);
		lblColor.setText("Color: ");
		
		btnRadioButtonGrey = new Button(compositeNorth, SWT.RADIO);
		btnRadioButtonGrey.setText("Grey");
		btnRadioButtonGrey.addSelectionListener(this);
		
		btnRadioButtonCustom = new Button(compositeNorth, SWT.RADIO);
		btnRadioButtonCustom.setText("Custom");
		btnRadioButtonCustom.addSelectionListener(this);
		
		btnRadioButtonRandom = new Button(compositeNorth, SWT.RADIO);
		btnRadioButtonRandom.setSelection(true);
		btnRadioButtonRandom.setText("Random");
		
		btnOneColor = new Button(compositeNorth, SWT.RADIO);
		btnOneColor.setText("One Color");
		btnOneColor.addSelectionListener(this);
		
		btnRadioButtonRandom.addSelectionListener(this);
		
		Composite compositeSouth = new Composite(this, SWT.NONE);
		compositeSouth.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		compositeSouth.setLayout(new GridLayout(5, false));
		
		btnCheckButton = new Button(compositeSouth, SWT.CHECK);
		btnCheckButton.setBounds(0, 0, 111, 20);
		btnCheckButton.setText("Show in Chart");
		btnCheckButton.addSelectionListener(this);
		
		labelShowColor = new Label(compositeSouth, SWT.BORDER);
		GridData gd_labelShowColor = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_labelShowColor.widthHint = 100;
		labelShowColor.setLayoutData(gd_labelShowColor);
		labelShowColor.setBounds(0, 0, 70, 20);
		labelShowColor.setText(" ");
		labelShowColor.addMouseListener(this);
		new Label(compositeSouth, SWT.NONE);
		new Label(compositeSouth, SWT.NONE);
		new Label(compositeSouth, SWT.NONE);
		
		loadSettings();

	}

	@Override
	protected void checkSubclass() {}

	@Override
	public void widgetSelected(SelectionEvent e) {
		
		if(e.getSource() == btnNewButton) {
			TreeDialogBar treeDialogBar = new TreeDialogBar(this.getShell(), SWT.NONE);
			Map.Entry<Term,TermSort> pair = treeDialogBar.open();
			selectedTerm = pair.getKey();
			termSort = pair.getValue();
			if(selectedTerm != null) {
				lblSelectedTermIs.setText("Selected Term is: '" + selectedTerm.getName()+"'");
				barTermList.clear();
				list.removeAll();
				btnRadioButtonRandom.setEnabled(true);
				switch(termSort) {
					case YEAR:{
						SettingsDialog.generalPageBar.setTitle("Number of cites per year of " + selectedTerm.getName());
						buildListPerYear();
						break;
					}
					case SUBCLASS:{
						SettingsDialog.generalPageBar.setTitle("Number of cites per subclass of " + selectedTerm.getName());
						buildListPerSubclass();
						break;
					}
				}
				list.setSelection(0);
				btnRadioButtonRandom.setSelection(true);
				refresh();
			}	
						
		}
		
		if(e.getSource() == btnCites) {
			barTermList.clear();
			list.removeAll();
			SortedMap<String, Integer> sortedMap = chartDataProvider.calculateCitesPerYear();	
			for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
				barTermList.add(new BarDataTerm(entry.getKey(), entry.getValue()));
				list.add("Year:    "+ entry.getKey() + "    (" +entry.getValue() + " entries)");
			}		
			
			
		}
		
		if(e.getSource() == list) {			
			refresh();			
		}
		
		if(e.getSource() == btnCheckButton) {
			int selectedItem = list.getSelectionIndex();
			barTermList.get(selectedItem).setDisplayed(btnCheckButton.getSelection());
		}
			
		if(e.getSource() == btnRadioButtonGrey && list.getItemCount() > 0) {
			
			int step = 255/barTermList.size();
			
			int rgbValue = 0;
			for(BarDataTerm barDataTerm: barTermList) {
				rgbValue = rgbValue + step;
				barDataTerm.setRgb(new RGB(rgbValue, rgbValue, rgbValue));				
			}
			refresh();
		}
		
		if(e.getSource() == btnRadioButtonRandom && list.getItemCount() > 0) {
			
			for(BarDataTerm barDataTerm: barTermList) {
				barDataTerm.setRGBRandom();				
			}
			refresh();
		}
		
		if(e.getSource() == btnOneColor && list.getItemCount() > 0 && btnOneColor.getSelection()) {
			
			RGB rgb = PageSupport.openAndGetColor(this.getParent(), labelShowColor);
			for(BarDataTerm barDataTerm: barTermList) {
				barDataTerm.setRgb(rgb);;				
			}
			refresh();
		}
		
	}
	
	private void buildListPerSubclass() {
		SortedMap<String, Integer> sortedMap = chartDataProvider.calculateNumberOfPapersPerClass(selectedTerm);	
		for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			barTermList.add(new BarDataTerm(entry.getKey(), entry.getValue()));
			list.add("Subterm:    " + entry.getKey() +"     (" +entry.getValue()+" entries)");
		}
		
	}

	private void buildListPerYear() {		
		SortedMap<String, Integer> sortedMap = chartDataProvider.calculateNumberOfCitesPerYearForClass(selectedTerm);	
		for(Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
			barTermList.add(new BarDataTerm(entry.getKey(), entry.getValue()));
			list.add("Year:    "+ entry.getKey() + "    (" +entry.getValue() + " entries)");
		}		
	}

	private void refresh() {
		int index = list.getSelectionIndex();
		if(index >= 0) {
		labelShowColor.setBackground(barTermList.get(index).getColor(this.getDisplay()));
		btnCheckButton.setSelection(barTermList.get(index).isDisplayed());
		this.layout();
		}
	}
	
	@Override
	public void mouseUp(MouseEvent e) {
		if(e.getSource() == labelShowColor && list.getItemCount() > 0 && btnRadioButtonCustom.getSelection()) {
			RGB rgb = PageSupport.openAndGetColor(this.getParent(), labelShowColor);
			int index = list.getSelectionIndex();
			barTermList.get(index).setRgb(rgb);
		}
	}
	@Override
	public void saveSettings() {
		
		settings.setBarTermList(barTermList);
		settings.setSelectedTerm(selectedTerm);
		settings.setTermSort(termSort);
			
	}
	@Override
	public void loadSettings() {
		barTermList = settings.getBarTermList();
		selectedTerm = settings.getSelectedTerm();
		termSort = settings.getTermSort();
		
		if(!barTermList.isEmpty()) {			
			if(termSort == TermSort.YEAR)
				buildListPerYear();
			else
				buildListPerSubclass();
		}		
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {}

	@Override
	public void mouseDoubleClick(MouseEvent e) {}

	@Override
	public void mouseDown(MouseEvent e) {}

	
}
