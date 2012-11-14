#ifndef TARGET_H
#define TARGET_H

#include <qglobal.h>

class QGraphicsItem;

class Target
{
public:

    Target();
    Target(qreal _X, qreal _Y);

    void setItem(QGraphicsItem* item, qreal offset_x, qreal offset_y);

    void setPos(qreal _X, qreal _Y);
    void clearPos();

    qreal x() const{ return m_X; }
    qreal y() const{ return m_Y; }
    bool isSet() const{ return m_IsSet; }

private:

    QGraphicsItem* m_Item;
    qreal m_ItemOffsetX;
    qreal m_ItemOffsetY;

    qreal m_X;
    qreal m_Y;

    bool m_IsSet;
};

#endif // TARGET_H
