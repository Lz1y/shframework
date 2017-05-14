package com.shframework.common.auth.resolver;

import java.util.List;
import java.util.Set;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

public class CustomWildcardPermission extends WildcardPermission {

	private static final long serialVersionUID = -6963433202890701252L;

	protected CustomWildcardPermission(){}
	
    public CustomWildcardPermission(String wildcardString){
        super(wildcardString);
    }
    public CustomWildcardPermission(String wildcardString, boolean caseSensitive) {
        super(wildcardString, caseSensitive);
    }

    @Override
    public boolean implies(Permission p) {
        if(!(p instanceof CustomWildcardPermission)){
            return false;
        }

        CustomWildcardPermission cwp = (CustomWildcardPermission)p;
        
        List<Set<String>> otherParts = cwp.getParts();
        int i = 0;
        for (Set<String> otherPart : otherParts) {
            if (getParts().size() - 1 < i) {
                return true;
            } else {
                Set<String> part = getParts().get(i);
                if (!part.contains(WILDCARD_TOKEN) && !part.containsAll(otherPart) && !otherPart.contains(WILDCARD_TOKEN)) {
                    return false;
                }
                i++;
            }
        }

        for (; i < getParts().size(); i++) {
            Set<String> part = getParts().get(i);
            if (!part.contains(WILDCARD_TOKEN)) {
                return false;
            }
        }

        return true;
    }
	
}
