package com.aperise.acl;

import com.aperise.bean.AclResource;

public interface AclResourceService {
    AclResource readBySource(String source);
}
