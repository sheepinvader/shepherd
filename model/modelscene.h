#ifndef MODELSCENE_H
#define MODELSCENE_H

#include <QGraphicsScene>
#include <QVector>
#include <QGraphicsItem>
#include <qmath.h>

#include "target.h"
#include "sheep.h"
#include "shepherd.h"

class QGraphicsSceneMouseEvent;

class ModelScene : public QGraphicsScene
{
    Q_OBJECT
public:

    ModelScene(QObject* parent = 0);

    void rndInit(size_t count);

signals:

    void gameWon();

public slots:

    void nextStep();

protected:

    void mouseReleaseEvent ( QGraphicsSceneMouseEvent * mouseEvent );

private:

    template<typename T1, typename T2>
    qreal distance(const T1& obj1, const T2& obj2)
    {
        qreal dx = obj2.x() - obj1.x();
        qreal dy = obj2.y() - obj1.y();
        return qSqrt(dx * dx + dy * dy);
    }

    template<typename T1, typename T2>
    qreal angle(const T1& obj1, const T2& obj2)
    {
        return qAtan2(obj2.y() - obj1.y(), obj2.x() - obj1.x());
    }

    Target         m_Target;
    Target         m_GameTarget;
    QVector<Sheep> m_Sheeps;
    Shepherd       m_Shepherd;
};

#endif // MODELSCENE_H
