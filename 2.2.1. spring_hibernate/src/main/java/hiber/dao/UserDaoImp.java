package hiber.dao;

import hiber.model.User;
import hiber.service.UserServiceImp;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
//сделал зависимость через конструктор
   private SessionFactory sessionFactory;

   UserDaoImp(SessionFactory sessionFactory){
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public List<User> getUserByCar(String model, int series) {
      Session session = sessionFactory.getCurrentSession();
      Query query = session.createQuery("SELECT u FROM User u INNER JOIN u.car uc WITH uc.model = :model AND uc.series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      List<User>users = query.getResultList();

//      return (User) query.getSingleResult();
      return users;
   }

}
