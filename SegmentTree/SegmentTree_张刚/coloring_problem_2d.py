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
    mark, color = True, 1 # 初始值为1, 代表无色
    block = [(-1, -1), (-1, -1)] # 左下角+右上角坐标
    childs = [] # 4/2/0个儿子结点

# 递归建立线段树
pos = 0 # 全局变量, 遍历数组tree
def build_tree(width, height):
    def build_tree(lcbtm, rctop):
        global pos
        cur, pos, childs = pos, pos + 1, []
        tree[cur].block = [lcbtm, rctop]
        (x1, y1), (x2, y2) = lcbtm, rctop
        if x2 - x1 > 1 and y2 - y1 > 1:
            mid_x = (x2 + x1 + 1) >> 1
            mid_y = (y2 + y1 + 1) >> 1
            childs.append(build_tree((x1,y1), (mid_x, mid_y)))
            childs.append(build_tree((mid_x,y1), (x2, mid_y)))
            childs.append(build_tree((x1,mid_y), (mid_x, y2)))
            childs.append(build_tree((mid_x,mid_y), (x2, y2)))
        elif x2 - x1 > 1:
            mid_x = (x2 + x1 + 1) >> 1
            childs.append(build_tree((x1, y1), (mid_x, y2)))
            childs.append(build_tree((mid_x, y1), (x2, y2)))
        elif y2 - y1 > 1:
            mid_y = (y2 + y1 + 1) >> 1
            childs.append(build_tree((x1, y1), (x2, mid_y)))
            childs.append(build_tree((x1, mid_y), (x2, y2)))
        tree[cur].childs = childs
        return cur
    build_tree((0, 0), (width - 1, height - 1))

# 先序打印线段树结点信息
def print_tree():
    def print_tree(v):
        print(tree[v].block, end= ' ' * (25 - len(str(tree[v].block))))
        print(tree[v].mark, counting_color(tree[v].color), color2str(tree[v].color), sep='\t')
        for child in tree[v].childs:
            print_tree(child)
    print('-' * 64)
    print_tree(0)
    print('-' * 64)

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

# 判断两个矩形区域是否有交集
def has_union(block1, block2):
    lcbtm1, rctop1 = block1
    lcbtm2, rctop2 = block2
    if rctop1[0] <= lcbtm2[0] or rctop1[1] <= lcbtm2[1]:return False # 1在2的左边或下边
    if rctop2[0] <= lcbtm1[0] or rctop2[1] <= lcbtm1[1]:return False # 2在1的左边或下边
    return True

# 判断block1是否包含在block2中
def is_included(block1, block2):
    lcbtm1, rctop1 = block1
    lcbtm2, rctop2 = block2
    if lcbtm1[0] >= lcbtm2[0] and lcbtm1[1] >= lcbtm2[1]:
        if rctop1[0] <= rctop2[0] and rctop1[1] <= rctop2[1]:
            return True
    return False

# 设置某一矩形区域为指定的颜色
def set_color(v, lcntm, rctop, color):
    # 与当前矩形区域无交集
    if not has_union(tree[v].block, [lcntm, rctop]):return

    # 包含当前矩形区域
    if is_included(tree[v].block, [lcntm, rctop]):
        tree[v].color, tree[v].mark = color, True; return

    # 当前矩形区域同色,标记和颜色下传
    if tree[v].mark:
        for child in tree[v].childs:
            tree[child].color, tree[child].mark = tree[v].color, True
        tree[v].mark = False

    # 递归操作所有子树
    for child in tree[v].childs:
        set_color(child, lcntm, rctop, color)

    # 合并所有子树的颜色
    if tree[v].childs: tree[v].color = 0
    for child in tree[v].childs:
        tree[v].color |= tree[child].color
    if counting_color(tree[v].color) == 1:
        tree[v].mark = True

# 查询给定矩形区域的颜色分布
def query(lcbtm, rctop):
    def query(v, lcbtm, rctop, nodes):
        if has_union(tree[v].block, [lcbtm, rctop]):
            if is_included(tree[v].block, [lcbtm, rctop]) and tree[v].mark:
                nodes.append(tree[v])
            else:
                if tree[v].mark: # 同色, 标记和颜色下传
                    for child in tree[v].childs:
                        tree[child].color, tree[child].mark = tree[v].color, True
                for child in tree[v].childs:
                    query(child, lcbtm, rctop, nodes)

    nodes, node_blocks, node_colors = [], [], []
    query(0, lcbtm, rctop, nodes)
    for node in nodes:
        node_blocks.append(node.block)
        node_colors.append('c-' + str(colors.index(node.color)))
    return list(zip(node_blocks, node_colors))

if __name__ == '__main__':
    WH = input('please input width and height of the matrix separated by space:')
    WH, tree = WH.split(' '), []
    width, height = int(WH[0]), int(WH[1])
    nodes_num = max(width-1, height-1) ** 2 * 8 // 3 # 待定
    colors = [2 ** i for i in range(32)]  # 32bits,每位代表一种颜色
    for i in range(nodes_num):
        tree.append(Node())

    build_tree(width, height); print_tree()

    set_color(0, (0,0),(3,2), colors[5]); print_tree()
    set_color(0, (2,0),(4,1), colors[10]);print_tree()
    set_color(0, (1,0),(3,2), colors[20]);print_tree()
    set_color(0, (0,1),(2,2), colors[16]);print_tree()

    print(query((1,1),(4,2)))
    print(query((0,0),(1,2)))
    print(query((0,0),(3,2)))
    print(query((0,0),(4,2)))
    print(query((2,0),(3,1)))
