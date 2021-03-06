package org.repositoryminer.codesmell;

import org.bson.Document;
import org.repositoryminer.ast.AST;
import org.repositoryminer.ast.AbstractTypeDeclaration;
import org.repositoryminer.ast.DeclarationType;
import org.repositoryminer.ast.TypeDeclaration;
import org.repositoryminer.metric.ATFDMetric;
import org.repositoryminer.metric.NOAMetric;
import org.repositoryminer.metric.TCCMetric;
import org.repositoryminer.metric.WMCMetric;

public class GodClass implements ICodeSmell {

	private int atfdThreshold = 40;
	private int wmcThreshold = 75;
	private float tccThreshold = 0.2f;
	private int noaThreshold = 20;
	private boolean useNoa = false;

	public GodClass() {
	};

	public GodClass(int atfdThreshold, int wmcThreshold, float tccThreshold, int noaThreshold, boolean useNoa) {
		this.atfdThreshold = atfdThreshold;
		this.wmcThreshold = wmcThreshold;
		this.tccThreshold = tccThreshold;
		this.noaThreshold = noaThreshold;
		this.useNoa = useNoa;
	}

	@Override
	public void detect(AbstractTypeDeclaration type, AST ast, Document document) {
		if (type.getType() == DeclarationType.CLASS_OR_INTERFACE) {
			TypeDeclaration cls = (TypeDeclaration) type;

			boolean godClass = detect(type, cls);
			document.append("name", new String("God Class")).append("value", new Boolean(godClass));
		}
	}

	public boolean detect(AbstractTypeDeclaration type, TypeDeclaration cls) {
		boolean godClass = false;
		
		ATFDMetric atfdMetric = new ATFDMetric();
		WMCMetric wmcMetric = new WMCMetric();
		TCCMetric tccMetric = new TCCMetric();
		NOAMetric noaMetric = new NOAMetric();

		int atfd = atfdMetric.calculate(type, cls.getMethods(), false);
		float tcc = tccMetric.calculate(type, cls.getMethods());
		int wmc = wmcMetric.calculate(cls.getMethods());
		int noa = noaMetric.calculate(cls.getFields());

		if(useNoa)
			godClass = (atfd > atfdThreshold) && ((wmc > wmcThreshold) || ((tcc < tccThreshold) && (noa > noaThreshold)));
		else
			godClass = (atfd > atfdThreshold) && ((wmc > wmcThreshold) || ((tcc < tccThreshold)));
		
		return godClass;
	}
}
