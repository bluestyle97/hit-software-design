"""
 Copyright(c) 2017 Gang Zhang
 All rights reserved.
 Author:Gang Zhang
 Date:2017.6.2
"""

#!/usr/bin/env python
# -*- coding: utf-8 -*-

import bisect

# 树结点
class Node:
    sorted_arr, block = [], [(-1,-1),(-1,-1)]
    childs = [] # 4/2/0个子结点

# 递归建立线段树
pos = 0  # 全局变量, 遍历数组tree
def build_tree(width, height):
    def merge2ways(arr1, arr2):
        arr = []
        while arr1 and arr2:
            if arr1[0] < arr2[0]:
                arr.append(arr1.pop(0))
            else:
                arr.append(arr2.pop(0))
        if arr1:arr.extend(arr1)
        if arr2:arr.extend(arr2)
        return arr

    def merge4ways(arr1, arr2, arr3, arr4):
        merge1 = merge2ways(arr1, arr2)
        merge2 = merge2ways(arr3, arr4)
        return merge2ways(merge1, merge2)

    def build_tree(lcbtm, rctop):
        global pos
        cur, cur_node = pos, tree[pos]
        pos, childs =  pos + 1, []
        tree[cur].block = [lcbtm, rctop]
        (x1, y1), (x2, y2) = lcbtm, rctop
        if x1 < x2 and y1 < y2:
            mid_x, mid_y = (x2 + x1) >> 1, (y2 + y1) >> 1
            childs.append(build_tree((x1, y1), (mid_x, mid_y)))
            childs.append(build_tree((mid_x + 1, y1), (x2, mid_y)))
            childs.append(build_tree((x1, mid_y + 1), (mid_x, y2)))
            childs.append(build_tree((mid_x + 1, mid_y + 1), (x2, y2)))
            cur_node.sorted_arr = merge4ways(tree[childs[0]].sorted_arr[:],
                tree[childs[1]].sorted_arr[:], tree[childs[2]].sorted_arr[:], tree[childs[3]].sorted_arr[:])
        elif x1 < x2:
            mid_x = (x2 + x1) >> 1
            childs.append(build_tree((x1, y1), (mid_x, y2)))
            childs.append(build_tree((mid_x + 1, y1), (x2, y2)))
            cur_node.sorted_arr = merge2ways(tree[childs[0]].sorted_arr[:], tree[childs[1]].sorted_arr[:])
        elif y1 < y2:
            mid_y = (y2 + y1) >> 1
            childs.append(build_tree((x1, y1), (x2, mid_y)))
            childs.append(build_tree((x1, mid_y + 1), (x2, y2)))
            cur_node.sorted_arr = merge2ways(tree[childs[0]].sorted_arr[:], tree[childs[1]].sorted_arr[:])
        else:
            cur_node.sorted_arr = [data[y1-1][x1-1]] # note
        tree[cur].childs = childs
        return cur

    build_tree((1,1), (width, height))

# 先序打印线段树结点信息
def print_tree():
    def print_tree(v):
        print(tree[v].block, end= ' ' * (10 - len(str(tree[v].block))))
        print(tree[v].sorted_arr)
        for child in tree[v].childs:
            print_tree(child)
    print_tree(0)

# 统计有序列表array中不大于value的元素数目
def search(array, value):
    if array[0] > value: return 0
    if array[-1] <= value: return len(array)
    return bisect.bisect_right(array, value)

# 判断两个矩形区域是否有交集
def has_union(block1, block2):
    lcbtm1, rctop1 = block1
    lcbtm2, rctop2 = block2
    if rctop1[0] < lcbtm2[0] or rctop1[1] < lcbtm2[1]:return False # 1在2的左边或下边
    if rctop2[0] < lcbtm1[0] or rctop2[1] < lcbtm1[1]:return False # 2在1的左边或下边
    return True

# 判断block1是否包含在block2中
def is_included(block1, block2):
    lcbtm1, rctop1 = block1
    lcbtm2, rctop2 = block2
    if lcbtm1[0] >= lcbtm2[0] and lcbtm1[1] >= lcbtm2[1]:
        if rctop1[0] <= rctop2[0] and rctop1[1] <= rctop2[1]:
            return True
    return False

def query(lcbtm, rctop, k):
    # 查询给定区间给小于value的元素数目
    def query(cur, lcbtm, rctop, value):
        if not has_union(tree[cur].block, [lcbtm, rctop]):return 0 # 无交集
        if is_included(tree[cur].block, [lcbtm, rctop]): # 包含当前结点
            return search(tree[cur].sorted_arr, value)
        # 递归操作左右子树
        sum_num = 0
        for child in tree[cur].childs:
            sum_num += query(child, lcbtm, rctop, value)
        return sum_num

    # 二分枚举查找
    small, big = min([min(line) for line in data]), max([max(line) for line in data])
    while small < big:
        mid_value = (small + big) >> 1
        result = query(0, lcbtm, rctop, mid_value)
        if result >= k: big = mid_value
        else: small = mid_value + 1
    return small

if __name__ == '__main__':
    # 输入数据
    str_data = input('Input the data line by line separated by space:\n').split('+')
    str_data = [line.split(' ') for line in str_data]
    data, tree = [[int(item) for item in line] for line in str_data], []
    for line in data:
        for value in line:
            print(value, end='\t')
        print()

    width, height = int(len(data[0])), int(len(data))
    nodes_num = max(width - 1, height - 1) ** 2 * 8 // 3  # 待定

    # 建立线段树
    for i in range(nodes_num):
        tree.append(Node())
    build_tree(width, height)
    # print_tree()

    # 交互查询
    while input('输入n退出,否则继续查询:') != 'n':
        lc_x, lc_y, rc_x, rc_y, k = [int(x) for x in input('请输入查询区间和K值,以空格分开:').split(' ')]
        print('第%s小:' % k, query((lc_x, lc_y), (rc_x, rc_y), k))

# 4 5 2 3 1+45 12 14 9 2+7 12 3 4 8