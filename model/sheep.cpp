#include "sheep.h"

#include <qmath.h>
#include <QGraphicsItem>

double Sheep::_PartyRadius = 50.0;
int    Sheep::_PartyCount  = 3;

Sheep::Sheep() : m_Item(0), m_DecorationItem(0), m_X(0.0), m_Y(0.0),
  m_IsLastRnd(false)
{
}

Sheep::Sheep(qreal _X, qreal _Y) : m_Item(0), m_DecorationItem(0),
    m_X(_X), m_Y(_Y), m_IsLastRnd(false)
{
}

void Sheep::setItem(QGraphicsItem *item, qreal offset_x, qreal offset_y)
{
    m_Item = item;
    m_OffsetX = offset_x;
    m_OffsetY = offset_y;
    m_Item->setPos(m_X + m_OffsetX, m_Y + m_OffsetY);
}

void Sheep::setDecorationItem(QGraphicsItem *item, qreal offset_x, qreal offset_y)
{
    m_DecorationItem = item;
    m_DecOffsetX = offset_x;
    m_DecOffsetY = offset_y;
    m_DecorationItem->setPos(m_X + m_DecOffsetX, m_Y + m_DecOffsetY);
}

qreal Sheep::PartyRadius()
{
    return _PartyRadius;
}

int Sheep::PartyCount()
{
    return _PartyCount;
}

void Sheep::setPartyRadius(qreal party_radius)
{
    _PartyRadius = party_radius;
}

void Sheep::setPartyCount(int party_count)
{
    _PartyCount = party_count;
}

void Sheep::rndMove(qreal len)
{
    qreal angle = static_cast<qreal>(qrand()) * M_PI / RAND_MAX / 4.0 - M_PI / 8.0;
    move(m_LastRndAngle + angle, len);
    m_LastRndAngle = m_LastRndAngle + angle;
    m_IsLastRnd    = true;
}

void Sheep::move(qreal angle, qreal len)
{
  m_IsLastRnd = false;
    m_X += qCos(angle) * len;
    m_Y += qSin(angle) * len;
    if (m_Item)
        m_Item->setPos(m_X + m_OffsetX, m_Y + m_OffsetY);
    if (m_DecorationItem)
        m_DecorationItem->setPos(m_X + m_DecOffsetX, m_Y + m_DecOffsetY);
}

void Sheep::deltaMove(qreal dX, qreal dY)
{
  m_IsLastRnd = false;
    m_X += dX;
    m_Y += dY;
    if (m_Item)
        m_Item->setPos(m_X + m_OffsetX, m_Y + m_OffsetY);
    if (m_DecorationItem)
        m_DecorationItem->setPos(m_X + m_DecOffsetX, m_Y + m_DecOffsetY);
}
