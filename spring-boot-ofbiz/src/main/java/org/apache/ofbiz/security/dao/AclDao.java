package org.apache.ofbiz.security.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.ofbiz.security.entity.AclClass;
import org.apache.ofbiz.security.entity.AclEntry;
import org.apache.ofbiz.security.entity.AclObjectIdentity;
import org.apache.ofbiz.security.entity.AclSid;
import org.springframework.security.acls.model.ObjectIdentity;

public interface AclDao {

    List<ObjectIdentity> findChildren(Serializable identifier, String type);

    AclObjectIdentity getObjectIdentity(String type, Serializable identifier);

    void createObjectIdentity(AclObjectIdentity identity);

    List<AclSid> findAclSidList(Boolean valueOf, String sidName);

    AclSid createAclSid(AclSid sid2);

    List<AclClass> findAclClassList(String type);

    AclClass createAclClass(AclClass clazz);

    void deleteEntries(AclObjectIdentity oidPrimaryKey);

    void deleteObjectIdentity(AclObjectIdentity oidPrimaryKey);

    void createEntries(List<AclEntry> entries);

    boolean updateObjectIdentity(AclObjectIdentity aclObject);

    AclSid findAclSid(String principal);

}
