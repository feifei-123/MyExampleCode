//
// Created by 飞飞 on 2018/11/5.
//

#ifndef MYEXAMPLECODE_CFOOD_H
#define MYEXAMPLECODE_CFOOD_H


#include <stdint.h>
#include <malloc.h>

class CFood {
    private:
        char * name;
        double price;
public:
    CFood(char*name, double price){
        this->name = name;
        this->price = price;
    }

    ~CFood(){
        if(name != NULL) {
            free(name);
            name = NULL;
        }
    }

    const char * getName(){
        return this->name;
    }

    double getPrice(){
        return this->price;
    }

};


#endif //MYEXAMPLECODE_CFOOD_H
