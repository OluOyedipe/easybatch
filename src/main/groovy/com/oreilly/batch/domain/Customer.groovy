package com.oreilly.batch.domain

import org.springframework.batch.item.ResourceAware
import org.springframework.core.io.Resource

/**
 * Created by oo185005 on 12/10/16.
 */


class Customer implements ResourceAware {
    Long id
    String lastName
    String firstName
    Date birthdate
    Resource resource

    @Override
    String toString() {
        "Customer{id=$id, firstName=$firstName, lastName=$lastName, birthdate=$birthdate} ${resource ? 'from ' + resource.description : ''}"
    }

    @Override
    void setResource(Resource resource) {
        this.resource = resource
    }
}
