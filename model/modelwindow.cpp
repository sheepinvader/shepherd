#include "modelwindow.h"
#include "ui_modelwindow.h"

#include <QTimer>
#include <QMessageBox>

#include "modelscene.h"

ModelWindow::ModelWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::ModelWindow),
    m_Timer(new QTimer(this))
{
    ui->setupUi(this);
    m_Scene = new ModelScene(this);
    ui->gvModel->setScene(m_Scene);
    m_Scene->rndInit(10);
    ui->sbParty->setValue(Sheep::PartyCount());
    ui->dsbParty->setValue(Sheep::PartyRadius());
    ui->sbCount->setValue(10);
    ui->dsbSmallR->setValue(Shepherd::SmallR());
    ui->dsbBigR->setValue(Shepherd::BigR());
    connect(m_Timer, SIGNAL(timeout()), m_Scene, SLOT(nextStep()));
    m_Timer->setInterval(10);
    m_Timer->start();
    connect(m_Scene, SIGNAL(gameWon()), SLOT(gameWon()));
}

ModelWindow::~ModelWindow()
{
    delete ui;
}

void ModelWindow::on_pushButton_clicked()
{
    Sheep::setPartyCount(ui->sbParty->value());
    Sheep::setPartyRadius(ui->dsbParty->value());
    Shepherd::setSmallR(ui->dsbSmallR->value());
    Shepherd::setBigR(ui->dsbBigR->value());
    m_Scene->rndInit(ui->sbCount->value());
    if (!m_Timer->isActive())
      m_Timer->start();
}

void ModelWindow::gameWon()
{
  m_Timer->stop();
  QMessageBox::information(this, "Game win", "Congratulations!");
}
