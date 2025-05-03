package org.mozilla.javascript.ast;

import org.mozilla.javascript.Token;

import java.util.ArrayList;
import java.util.List;

/**
 * Node used for import statements, as well as
 * a base class for the ExportNode.
 */
public class ImportNode extends AstNode {
    private final List<ModuleBinding> namedBindings = new ArrayList<>();
    private ModuleBinding defaultBinding = null;
    private ModuleBinding namespaceBinding = null;
    private Name modulePath = null;

    {
        type = Token.IMPORT;
    }

    public List<ModuleBinding> getNamedBindings() {
        return namedBindings;
    }

    public void addNamedBinding(Name targetName, Name scopeName) {
        namedBindings.add(new ModuleBinding(targetName, scopeName));
    }

    public ModuleBinding getDefaultBinding() {
        return defaultBinding;
    }

    public void setDefaultBinding(Name scopeName) {
        defaultBinding = new ModuleBinding(null, scopeName);
    }

    public void setNamespaceBinding(Name scopeName) {
        namespaceBinding = new ModuleBinding(null, scopeName);
    }

    public ModuleBinding getNamespaceBinding() {
        return namespaceBinding;
    }

    @Override
    public String toSource(int depth) {
        StringBuilder sb = new StringBuilder("import ");

        if (namespaceBinding != null) {
            sb.append("* ");

            if (defaultBinding.scopeName != null) {
                sb.append(" as ").append(defaultBinding.scopeName);
            }
        } else if (defaultBinding != null) {
            sb.append(defaultBinding.scopeName);
        }

        if (namespaceBinding == null) {
            if (defaultBinding != null) {
                sb.append(",");
            }

            sb.append(" {");

            for (int i = 0, namedBindingSize = namedBindings.size(); i < namedBindingSize; i++) {
                ModuleBinding imp = namedBindings.get(i);

                sb.append(' ').append(imp.targetName);

                if (imp.scopeName != null) {
                    sb.append(" as ").append(imp.scopeName);
                }

                if (i != namedBindingSize - 1) {
                    sb.append(',');
                }
            }

            sb.append(" } ");
        }

        sb.append("from '").append(modulePath).append("';");

        return sb.toString();
    }

    @Override
    public void visit(NodeVisitor v) {
        v.visit(this);
    }

    public Name getModulePath() {
        return modulePath;
    }

    public void setModulePath(Name modulePath) {
        this.modulePath = modulePath;
    }

    public static class ModuleBinding {
        private final Name targetName;
        private final Name scopeName;

        public ModuleBinding(Name targetName, Name scopeName) {
            this.targetName = targetName;
            this.scopeName = scopeName;
        }

        public Name getTargetName() {
            return targetName;
        }

        public Name getScopeName() {
            return scopeName;
        }
    }
}
