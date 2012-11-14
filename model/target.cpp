#include "target.h"

#include <QGraphicsItem>

Target::Target() : m_Item(0), m_X(0.0), m_Y(0.0), m_IsSet(false)
{
}

Target::Target(qreal _X, qreal _Y) : m_Item(0), m_X(_X), m_Y(_Y), m_IsSet(true)
{
}

void Target::setItem(QGraphicsItem *item, qreal offset_x, qreal offset_y)
{
    m_Item = item;
    m_ItemOffsetX = offset_x;
    m_ItemOffsetY = offset_y;
    m_Item->setPos(m_X + m_ItemOffsetX, m_Y + m_ItemOffsetY);
}

void Target::setPos(qreal _X, qreal _Y)
{
    m_X = _X;
    m_Y = _Y;
    m_IsSet = true;
    if (m_Item)
    {
        m_Item->setVisible(true);
        m_Item->setPos(m_X + m_ItemOffsetX, m_Y + m_ItemOffsetY);
    }
}

void Target::clearPos()
{
    m_IsSet = false;
    if (m_Item)
        m_Item->setVisible(false);
}
