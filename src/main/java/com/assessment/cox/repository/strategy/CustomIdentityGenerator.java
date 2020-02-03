package com.assessment.cox.repository.strategy;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import java.io.Serializable;
import org.hibernate.id.IdentityGenerator;

/**
 * Created by akhilesh on 2/1/20.
 */
public class CustomIdentityGenerator  extends IdentityGenerator{

  @Override
  public Serializable generate(SharedSessionContractImplementor si, Object ob) throws HibernateException{
    Serializable id = si.getEntityPersister(null, ob).getClassMetadata().getIdentifier(ob, si);
    return id!=null ? id : super.generate(si,ob);
  }
}
