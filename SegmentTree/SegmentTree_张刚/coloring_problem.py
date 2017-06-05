"""
 Copyright(c) 2017 Gang Zhang
 All rights reserved.
 Author:Gang Zhang
 Date:2017.6.2
"""

#!/usr/bin/env python
# -*- coding: utf-8 -*-

# 树结点
class Node:
    mark, color = True, 1 # 初始值为1,代表无色
    block = [-1, -1]      # 结点区间范围
    lchild, rchild = -1, -1 # 左右子结点

# 递归建立线段树
pos = 0 # 全局变量, 遍历数组tree
def build_tree(length):
    def build_tree(low, high):
        global pos
        cur, pos = pos, pos + 1
        tree[cur].block = [low, high]
        if high - low > 1:
            mid = (high + low + 1) >> 1
            tree[cur].lchild = build_tree(low, mid)
            tree[cur].rchild = build_tree(mid, high)
        return cur
    build_tree(0, length - 1)

# 先序打印线段树结点信息
def print_tree():
    def print_tree(v):
        print(tree[v].block, end= ' ' * (10 - len(str(tree[v].block))))
        print(tree[v].mark, counting_color(tree[v].color), color2str(tree[v].color), sep='\t')
        if tree[v].lchild != -1:
            print_tree(tree[v].lchild)
            print_tree(tree[v].rchild)
    print('-' * 52)
    print_tree(0)
    print('-' * 52)

# 统计结点颜色数,32bits,每位代表一种颜色
def counting_color(color):
    color_num = 0
    for k in range(32):
        color_num += (color == ((color >> 1) * 2 + 1))
        color = color >> 1
    return color_num

# 输出结点颜色种类,32bits,每位代表一种颜色
def color2str(color):
    color_str = ''
    for k in range(32):
        color_str += str(int(color == ((color >> 1) * 2 + 1)))
        color = color >> 1
    return color_str[::-1]

# 设置某一段为指定的颜色
def set_color(v, lhs, rhs, color):
    # 与当前结点无交集
    if tree[v].block[1] <= lhs or tree[v].block[0] >= rhs:return

    # 包含当期结点
    if lhs <= tree[v].block[0] and tree[v].block[1] <= rhs:
        tree[v].color, tree[v].mark = color, True; return

    # 当前结点同色,标记和颜色下传
    if tree[v].mark :
        tree[tree[v].lchild].color = tree[tree[v].rchild].color = tree[v].color
        tree[tree[v].lchild].mark = tree[tree[v].rchild].mark = True
        tree[v].mark = False

    # 递归操作左右子树
    set_color(tree[v].lchild, lhs, rhs, color)
    set_color(tree[v].rchild, lhs, rhs, color)

    # 合并左右子树的颜色
    tree[v].color = tree[tree[v].lchild].color | tree[tree[v].rchild].color
    if counting_color(tree[v].color == 1):
        tree[v].mark = True

# 查询给定区间的颜色标注序列
def query(lhs, rhs):
    def query(v, lhs, rhs, nodes):
        if tree[v].block[1] > lhs and tree[v].block[0] < rhs:
            if lhs <= tree[v].block[0] and tree[v].block[1] <= rhs and tree[v].mark == True:
                nodes.append(tree[v])
            else:
                if tree[v].mark: # 同色, 标记和颜色下传
                    tree[tree[v].lchild].color = tree[tree[v].rchild].color = tree[v].color
                    tree[tree[v].lchild].mark = tree[tree[v].rchild].mark = True
                query(tree[v].lchild, lhs, rhs, nodes)
                query(tree[v].rchild, lhs, rhs, nodes)

    nodes, node_blocks, node_colors = [], [], []
    query(0, lhs, rhs, nodes)
    for node in nodes:
        node_blocks.append(node.block)
        node_colors.append('c-' + str(colors.index(node.color)))
    return list(zip(node_blocks, node_colors))


if __name__ == '__main__':
    node_num, tree = int(input('please input number of the nodes:')), []
    colors = [2 ** i for i in range(32)]  # 32bits,每位代表一种颜色
    for i in range(2 * node_num - 1):
        tree.append(Node())

    build_tree(node_num); print_tree()
    set_color(0, 2, 5, colors[5]); print_tree()
    set_color(0, 4, 7, colors[8]); print_tree()
    set_color(0, 3, 6, colors[7]); print_tree()
    set_color(0, 5, 9, colors[9]); print_tree()

    print(query(4, 9))
    print(query(3, 7))
    print(query(2, 5))
    print(query(0, 9))
    print(query(3, 4))