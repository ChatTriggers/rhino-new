package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

public class ExportNode extends ImportNode {
    private AstNode exportedValue = null;
    private boolean defaultExport = false;

    public ExportNode() {
        super();
        type = Token.EXPORT;
    }

    public AstNode getExportedValue() {
        return exportedValue;
    }

    public void setExportedValue(AstNode exportedValue) {
        this.exportedValue = exportedValue;
    }

    public boolean isDefaultExport() {
        return defaultExport;
    }

    public void setDefaultExport() {
        this.defaultExport = true;
    }
}