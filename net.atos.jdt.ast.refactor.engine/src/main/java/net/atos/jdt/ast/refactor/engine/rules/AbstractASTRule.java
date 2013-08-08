/*
 *     Eclipse AST Validation, lite framework to validate java code
 *     
 *     Copyright (C) 2013 Atos Worldline or third-party contributors as
 *     indicated by the @author tags or express copyright attribution
 *     statements applied by the authors.
 *     
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License.
 *     
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *     Lesser General Public License for more details.
 *     
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package net.atos.jdt.ast.refactor.engine.rules;

import net.atos.jdt.ast.refactor.engine.internal.Activator;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.text.edits.TextEdit;

/**
 * Abstract AST Rule. this class overrides the AST Visitor one.
 * 
 * This means that all the methods for AST Visitor can be used, except the
 * visit(CompilationUnit) one.
 * 
 * Additionally, the abstract implementation contains helping methods
 * (addXXXMarker) to automatically create markers at the right place in the
 * code.
 * 
 * @author mvanbesien
 * @since 1.0
 */
public abstract class AbstractASTRule extends ASTVisitor {

	private boolean write;

	protected void setDirty() {
		this.write = true;
	}

	public void run(ICompilationUnit compilationUnit) {
		final ASTParser parser = ASTParser.newParser(AST.JLS4);
		parser.setSource(compilationUnit);
		parser.setStatementsRecovery(true);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(false);
		final CompilationUnit domCompilationUnit = (CompilationUnit) parser.createAST(new NullProgressMonitor());
		domCompilationUnit.recordModifications();
		try {
			domCompilationUnit.accept(this);
			if (this.write) {
				Document document = new Document(compilationUnit.getSource());
				TextEdit rewrite = domCompilationUnit.rewrite(document,
						compilationUnit.getJavaProject().getOptions(true));
				rewrite.apply(document);
				String newSource = document.get();
				compilationUnit.getBuffer().setContents(newSource);
				compilationUnit.getBuffer().save(new NullProgressMonitor(), true);
			}
		} catch (Exception e) {
			Activator
					.getDefault()
					.getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID,
							"An exception was caught while executing rule <" + this.getClass().getName() + "> on <"
									+ compilationUnit.getElementName() + ">", e));
			e.printStackTrace();
		}
	}
}
