#include <QtGui/QApplication>
#include "modelwindow.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    ModelWindow w;
    w.show();
    
    return a.exec();
}
