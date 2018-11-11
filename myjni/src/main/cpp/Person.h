//
// Created by 飞飞 on 2018/11/7.
//

#ifndef TESTJNI_PERSON_H
#define TESTJNI_PERSON_H


class Person {
private:
    int age;
    int * p;

public:
    long mJavaObj;
public:
    Person();
    int getAge();
    void setAge(int age);
    void initSDK();
    void releaseSDK();
public:
    ~Person();
};


#endif //TESTJNI_PERSON_H
