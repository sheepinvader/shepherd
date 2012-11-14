#ifndef SHEPHERD_H
#define SHEPHERD_H

#include <qglobal.h>

class QGraphicsItem;

class Shepherd
{
public:
    Shepherd();
    Shepherd(qreal _X, qreal _Y);

    qreal x() const{ return m_X; }
    qreal y() const{ return m_Y; }

    void setItem(QGraphicsItem* item, qreal offset_x, qreal offset_y);
    void setDecorationItem(QGraphicsItem* item, qreal offset_x, qreal offset_y);

    void deltaMove(qreal dX, qreal dY);
    void move(qreal angle, qreal len);

    static qreal SmallR();
    static qreal BigR();
    static void setSmallR(qreal small_R);
    static void setBigR(qreal big_R);

private:

    static qreal _SmallR;
    static qreal _BigR;

    QGraphicsItem* m_Item;
    qreal m_ItemOffsetX;
    qreal m_ItemOffsetY;
    QGraphicsItem* m_DecorationItem;
    qreal m_DecOffsetX;
    qreal m_DecOffsetY;

    qreal m_X;
    qreal m_Y;
};

#endif // SHEPHERD_H
