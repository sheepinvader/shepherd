#include "modelscene.h"

#include <QGraphicsEllipseItem>
#include <QGraphicsSceneMouseEvent>
#include <qglobal.h>

ModelScene::ModelScene(QObject *parent)
    : QGraphicsScene(parent)
{
}

//! Генерируем начальную сцену случайным образом
void ModelScene::rndInit(size_t count)
{
    // удаляем старые элементы сцены
    foreach(QGraphicsItem* item, items())
    {
        removeItem(item);
        delete item;
    }
    // добавляем цель
    m_Target.setItem(addEllipse(0.0, 0.0, 10.0, 10.0, QPen(), QBrush(QColor(255,0,0))), -5.0, -5.0);
    m_Target.clearPos();
    // добавляем пастуха
    m_Shepherd = Shepherd(0.0, 0.0);
    m_Shepherd.setItem(addEllipse(0.0, 0.0, 10.0, 10.0, QPen(), QBrush(QColor(0,0,255))), -5.0, -5.0);
    // добавляем овец
    m_Sheeps.clear();
    m_Sheeps.resize(count);
    for (size_t i = 0; i < count; ++i)
    {
        m_Sheeps[i] = Sheep(static_cast<double>(qrand()) * 100.0 / RAND_MAX - 50.0,
                            static_cast<double>(qrand()) * 100.0 / RAND_MAX - 50.0);
        m_Sheeps[i].setItem(addEllipse(0.0, 0.0, 10.0, 10.0, QPen(), QBrush(QColor(0, 255,0))), -5.0, -5.0);
    }
    // добавляем цель игры
    m_GameTarget = Target(static_cast<double>(qrand()) * 100.0 / RAND_MAX,
                          static_cast<double>(qrand()) * 100.0 / RAND_MAX);
    m_GameTarget.setItem(addEllipse(0.0, 0.0, 60.0, 60.0), -30.0, -30.0);
}

//! Функция, рассчитывающая следующий шаг
/*!
  Алгоритм движения овцы в simple модели следующий:
  Если овца видит пастуха, то она бежит в сторону центра масс стада.
*/
void ModelScene::nextStep()
{
    qreal sheep_run     = 3.0 / 5.0;
    qreal sheep_bee     = 1.0 / 5.0;
    qreal shepherd_move = 3.0 / 5.0;
    // old model in comment
/*
//! считаем количество соседей ОВЦЫ
    QVector<int> neighbours;
    QVector<int> herd;
    QVector<bool> is_herd;
    neighbours.resize(m_Sheeps.size());
    herd.resize(m_Sheeps.size());
    is_herd.resize(m_Sheeps.size());
    int herd_num = 1;
    for (int i = 0; i < m_Sheeps.size(); i++)
        for (int j = i + 1; j < m_Sheeps.size(); j++)
        {
            qreal dist = distance<Sheep, Sheep>(m_Sheeps[i], m_Sheeps[j]);
            if (dist < Sheep::PartyRadius())
            {
                if (herd[i])
                {
                  neighbours[i] ++;
                  neighbours[j] = neighbours[i];
                  herd[j] = herd[i];
                }
                else
                {
                  neighbours[i] = neighbours[j] = 1;
                  herd[i] = herd[j] = herd_num;
                  herd_num++;
                }
            }
        }
    int herd_count = herd_num;
    QVector<QPointF> herd_centers;
    for (int i = 1; i < herd_count; i++)
    {
      qreal sum_x = 0.0;
      qreal sum_y = 0.0;
      int count = 0;
      QVector<int> sheep_nums;
      for (int j = 0; j < herd.size(); ++j)
      {
        if (herd[j] == i)
        {
          sum_x += m_Sheeps[j].x();
          sum_y += m_Sheeps[j].y();
          sheep_nums.append(j);
          count++;
        }
      }
      if (count >= Sheep::PartyCount())
      {
        herd_centers.append(QPointF(sum_x / count, sum_y / count));
        foreach(int num, sheep_nums)
          is_herd[num] = true;
      }
}

    /*! перемещаем овцу, варианта три:
      * овца в Small_R пастуха - бежим от пастуха
      * овца в туссовке - совершаем случайное маленькое перемещение - пассемся
      * овца вне туссовки - ищем туссовку двигаясь на основе силы притяжения остальных овец,
      прибавляя малое случайное перемещение.
    */
    //! расчитываем центр масс овечек
    qreal sum_x = 0.0;
    qreal sum_y = 0.0;
    for (int j = 0; j < m_Sheeps.size(); ++j)
    {
      sum_x += m_Sheeps[j].x();
      sum_y += m_Sheeps[j].y();
    }
    QPointF sheeps_center(sum_x/m_Sheeps.size(),
                         sum_y/m_Sheeps.size());

    bool isGameWin = true;
    for (int i = 0; i < m_Sheeps.size(); ++i)
    {
        // расчитываем расстояния до пастуха и центра масс
        qreal dist_to_shepherd    = distance<Sheep, Shepherd>(m_Sheeps[i], m_Shepherd);
        qreal dist_sheperd_center = distance<Sheep, QPointF>(m_Sheeps[i], sheeps_center);
        qreal dist_to_center      = distance<Sheep, QPointF>(m_Sheeps[i], sheeps_center);

        qreal dist_sheperd_1      = 20.0;
        qreal dist_sheperd_2      = 20.0;
        qreal dist_sheep_center_1 = 10.0;

        // проверяем условие победы
        qreal dist_to_target   = distance<Sheep, Target>(m_Sheeps[i], m_GameTarget);
        if (dist_to_target > 30.0)
          isGameWin = false;

        // если расстояние до пастуха больше криттического
        if (dist_to_shepherd > dist_sheperd_1)
          // то свершаем случайные движения
          m_Sheeps[i].rndMove(sheep_bee);
        // иначе, если расстояния от пастуха до центра больше критического
        else if (dist_sheperd_center > dist_sheperd_2)
        {
          // то если расстояние до центра масс меньше критического
          if (dist_to_center < dist_sheep_center_1)
            // то совершаем случайное движение
            m_Sheeps[i].rndMove(sheep_bee);
          else
          {
            // иначе бежим к центру масс
            qreal a = qAtan2(sum_y/m_Sheeps.size() - m_Sheeps[i].y(),
                             sum_x/m_Sheeps.size() - m_Sheeps[i].x());
            m_Sheeps[i].move(a, sheep_run);
          }
        }
        else
        {
          // иначе бежим от пастуха
          qreal a = qAtan2(m_Sheeps[i].y() - m_Shepherd.y(),
                           m_Sheeps[i].x() - m_Shepherd.x());
          m_Sheeps[i].move(a, sheep_run);
        }
    }
    /*
        else
        {
            //! овца вне туссовки
          int   near_herd = 0;
          qreal min_dist  = distance<Sheep, QPointF>(m_Sheeps[i], herd_centers[0]);
          for (int j = 0; j < herd_centers.size(); j++)
          {
            qreal cur_dist = distance<Sheep, QPointF>(m_Sheeps[i], herd_centers[j]);
            if (min_dist > cur_dist)
            {
              near_herd = j;
              min_dist  = cur_dist;
            }
          }
          qreal a = angle<Sheep, QPointF>(m_Sheeps[i], herd_centers[near_herd]);
          m_Sheeps[i].move(a, sheep_run);
        }
    }
    */
    // двигаем пастуха, если есть цель
    if (m_Target.isSet())
    {
        qreal a = angle<Shepherd, Target>(m_Shepherd, m_Target);
        qreal d = distance<Shepherd, Target>(m_Shepherd, m_Target);
        if (d < 2.0)
          m_Target.clearPos();
        m_Shepherd.move(a, shepherd_move);
    }
    if (isGameWin)
      emit gameWon();
}

void ModelScene::mouseReleaseEvent(QGraphicsSceneMouseEvent *mouseEvent)
{
    if (mouseEvent->button() == Qt::LeftButton)
        m_Target.setPos(mouseEvent->scenePos().x(),
                      mouseEvent->scenePos().y());
    else
        m_Target.clearPos();
}
