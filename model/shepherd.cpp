#include "shepherd.h"

#include <QGraphicsItem>
#include <qmath.h>

double Shepherd::_SmallR = 30.0;
double Shepherd::_BigR   = 50.0;

Shepherd::Shepherd() : m_Item(0), m_X(0.0), m_Y(0.0), m_DecorationItem(0)
{
}

Shepherd::Shepherd(qreal _X, qreal _Y)
    : m_X(_X), m_Y(_Y), m_DecorationItem(0)
{
}

void Shepherd::setItem(QGraphicsItem *item, qreal offset_x, qreal offset_y)
{
    m_Item = item;
    m_ItemOffsetX = offset_x;
    m_ItemOffsetY = offset_y;
    m_Item->setPos(m_X + m_ItemOffsetX, m_Y + m_ItemOffsetY);
}

void Shepherd::setDecorationItem(QGraphicsItem *item, qreal offset_x, qreal offset_y)
{
    m_DecorationItem = item;
    m_DecOffsetX = offset_x;
    m_DecOffsetY = offset_y;
    m_DecorationItem->setPos(m_X + m_DecOffsetX, m_Y + m_DecOffsetY);
}

void Shepherd::deltaMove(qreal dX, qreal dY)
{
    m_X += dX;
    m_Y += dY;
    if (m_Item)
        m_Item->setPos(m_X + m_ItemOffsetX, m_Y + m_ItemOffsetY);
    if (m_DecorationItem)
        m_DecorationItem->setPos(m_X + m_DecOffsetX, m_Y + m_DecOffsetY);
}

void Shepherd::move(qreal angle, qreal len)
{
    m_X += qCos(angle) * len;
    m_Y += qSin(angle) * len;
    if (m_Item)
        m_Item->setPos(m_X + m_ItemOffsetX, m_Y + m_ItemOffsetY);
    if (m_DecorationItem)
        m_DecorationItem->setPos(m_X + m_DecOffsetX, m_Y + m_DecOffsetY);
}

qreal Shepherd::SmallR()
{
    return _SmallR;
}

qreal Shepherd::BigR()
{
    return _BigR;
}

void Shepherd::setSmallR(qreal small_R)
{
    _SmallR = small_R;
}

void Shepherd::setBigR(qreal big_R)
{
    _BigR = big_R;
}
