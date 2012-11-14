#ifndef SHEEP_H
#define SHEEP_H

#include <qglobal.h>

class QGraphicsItem;

class Sheep
{
public:

    Sheep();
    Sheep(qreal _X, qreal _Y);

    qreal x() const{ return m_X; }
    qreal y() const{ return m_Y; }

    void setItem(QGraphicsItem* item, qreal offset_x, qreal offset_y);
    void setDecorationItem(QGraphicsItem* item, qreal offset_x, qreal offset_y);

    //! случайно двигает овцу на заданное перемещение
    void rndMove(qreal len);
    //! перемещает овцу в данном направлении на данную длину
    void move(qreal angle, qreal len);
    //! перемещает овцу на заданные delta
    void deltaMove(qreal dX, qreal dY);

    static qreal PartyRadius();
    static int   PartyCount();

    static void setPartyRadius(qreal party_radius);
    static void setPartyCount(int party_count);

private:

    // радиус сбивания в стадо
    static qreal _PartyRadius;
    // количество для сбиания в стадо
    static int   _PartyCount;

    QGraphicsItem* m_Item;
    qreal m_OffsetX;
    qreal m_OffsetY;
    QGraphicsItem* m_DecorationItem;
    qreal m_DecOffsetX;
    qreal m_DecOffsetY;

    qreal m_X;
    qreal m_Y;

    qreal m_LastRndAngle;
    bool m_IsLastRnd;
};

#endif // SHEEP_H
