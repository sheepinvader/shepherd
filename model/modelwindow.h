#ifndef MODELWINDOW_H
#define MODELWINDOW_H

#include <QMainWindow>

namespace Ui {
class ModelWindow;
}

class QTimer;
class ModelScene;

class ModelWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit ModelWindow(QWidget *parent = 0);
    ~ModelWindow();

private slots:
    void on_pushButton_clicked();
    void gameWon();

private:
    Ui::ModelWindow *ui;
    ModelScene* m_Scene;
    QTimer*     m_Timer;
};

#endif // MODELWINDOW_H
