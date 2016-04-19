package org.repositoryminer.metric;

import org.bson.Document;
import org.repositoryminer.ast.AST;
import org.repositoryminer.ast.TypeDeclaration;

/**
 * Metrics calculations definition.
 */
public interface IMetric {

	public void calculate(TypeDeclaration type, AST ast, Document document);

}