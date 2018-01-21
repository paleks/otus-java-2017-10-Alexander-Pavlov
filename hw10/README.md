Домашнее задание 10
На основе ДЗ 9:
1. Оформить решение в виде DBService (interface DBService, class DBServiceImpl, UsersDAO, UsersDataSet,
Executor)
2. Не меняя интерфейс DBSerivice сделать DBServiceHibernateImpl на Hibernate.
3. Добавить в UsersDataSet поля:
адресс (OneToOne)
class AddressDataSet{
private String street;
}
и телефон* (OneToMany)
class PhoneDataSet{
private String number;
}
Добавить соответствущие датасеты и DAO.
* можно не поддерживать в ДЗ 9