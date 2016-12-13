package com.oreilly.batch.domain

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet
import org.springframework.validation.BindException

/**
 * Created by oo185005 on 12/10/16.
 */
class CustomerFieldSetMapper implements FieldSetMapper<Customer> {
    @Override
    Customer mapFieldSet(FieldSet fieldSet) throws BindException {
        new Customer().with {
            id = fieldSet.readLong('id')
            firstName = fieldSet.readString('firstName')
            lastName = fieldSet.readString('lastName')
            birthdate = fieldSet.readDate('birthdate', 'yyyy-MM-dd HH:mm:ss')

            it
        }
    }
}
