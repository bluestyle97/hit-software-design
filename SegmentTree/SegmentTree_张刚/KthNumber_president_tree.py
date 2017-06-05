"""
 Copyright(c) 2017 Gang Zhang
 All rights reserved.
 Author:Gang Zhang
 Date:2017.6.2
"""

#!/usr/bin/env python
# -*- coding: utf-8 -*-

import math

# 树结点
class Node:
    cnt, lchild, rchild = 0, -1, -1

# 递归建立第0棵线段树
pos = 0 # 全局变量,遍历内存池
def build_tree(size):
    def build_tree(low, high):
        global pos
        cur, pos = pos, pos + 1
        if low < high:
            mid = (high + low) >> 1
            pool[cur].lchild = build_tree(low, mid)
            pool[cur].rchild = build_tree(mid + 1, high)
        return cur
    return build_tree(1, size)

# debug
def print_tree(v):
    print(pool[v].cnt, end='\t')
    if pool[v].lchild != -1:
        print_tree(pool[v].lchild)
        print_tree(pool[v].rchild)

# 在前一棵线段树的基础上构建此棵线段树
def update(pre, low, high, value):
    def copy_node(node1, node2):
        node2.cnt, node2.lchild, node2.rchild = node1.cnt, node1.lchild, node1.rchild

    global pos
    cur, pos = pos, pos + 1
    cur_node, pre_node = pool[cur], pool[pre]
    copy_node(pre_node, cur_node)
    if low == high == value:
        cur_node.cnt += 1
        return cur

    mid = (low + high) >> 1
    if value <= mid:
        cur_node.lchild = update(pre_node.lchild, low, mid, value)
    else:
        cur_node.rchild = update(pre_node.rchild, mid + 1, high, value)
    cur_node.cnt = pool[cur_node.lchild].cnt + pool[cur_node.rchild].cnt
    return cur

# 查询任意区间的第K小值
def query(low, high, k):
    def query(pre_node, cur_node, low, high, k):
        if low == high: return low
        mid = (low + high) >> 1
        diff_num = pool[cur_node.lchild].cnt - pool[pre_node.lchild].cnt
        if k <= diff_num:
            return query(pool[pre_node.lchild], pool[cur_node.lchild], low, mid, k)
        else:
            return query(pool[pre_node.rchild], pool[cur_node.rchild], mid + 1, high, k - diff_num)
    return query(pool[root[low - 1]], pool[root[high]], 1, size, k)

if __name__ == '__main__':
    # 输入数据
    str_data = input('Input the data separated by space:').split(' ')
    data = [int(x) for x in str_data]

    # 离散化
    operated_data = sorted(list(set(data)))  # 去重、排序
    discretized_data = [operated_data.index(x) + 1 for x in data]

    # 建立内存池
    pool, size = [], len(operated_data)
    max_n = len(data) * (int(math.log2(2 * size - 1 )) + 1) + 2 * size - 1 # 数目待定
    for i in range(max_n):
        pool.append(Node())

    # 建立主席树, 保存每一颗线段树的根结点
    root = [build_tree(size)]
    for x in discretized_data:
        root.append(update(root[-1], 1, size, x))

    # for r in root:
    #     print_tree(r)

    # 交互查询
    while input('输入n退出,否则继续查询:') != 'n':
        low, high, k = [int(x) for x in input('请输入查询区间和K值,以空格分开:').split(' ')]
        print('第%s小:' % k, operated_data[query(low, high, k) - 1])