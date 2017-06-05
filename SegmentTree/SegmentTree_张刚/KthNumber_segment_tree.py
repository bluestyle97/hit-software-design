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
    sorted_arr, block = [], [-1, -1]
    lchild, rchild = -1, -1 # 左右子结点

# 递归建立线段树
pos = 0  # 全局变量, 遍历数组tree
def build_tree(size):
    def build_tree(low, high):
        global pos
        cur, cur_node, pos = pos, tree[pos], pos + 1
        tree[cur].block = [low, high]
        if low < high:
            mid = (high + low) >> 1
            cur_node.lchild = build_tree(low, mid)
            cur_node.rchild = build_tree(mid + 1, high)

        # 归并排序
        if low < high:
            arr, lc_arr =[], tree[cur_node.lchild].sorted_arr[:]
            rc_arr = tree[cur_node.rchild].sorted_arr[:]
            while lc_arr and rc_arr:
                if lc_arr[0] < rc_arr[0]:
                    arr.append(lc_arr.pop(0))
                else:
                    arr.append(rc_arr.pop(0))
            if lc_arr: arr.extend(lc_arr)
            if rc_arr: arr.extend(rc_arr)
            cur_node.sorted_arr = arr
        else:
            cur_node.sorted_arr = [tmp_data.pop(0)]
        return cur
    build_tree(1, size)

# 先序打印线段树结点信息
def print_tree():
    def print_tree(v):
        print(tree[v].block, end= ' ' * (10 - len(str(tree[v].block))))
        print(tree[v].sorted_arr)
        if tree[v].lchild != -1:
            print_tree(tree[v].lchild)
            print_tree(tree[v].rchild)
    print_tree(0)

# 统计有序列表array中不大于value的元素数目
def search(array, value):
    if array[0] > value: return 0
    if array[-1] <= value: return len(array)
    return bisect.bisect_right(array, value)

def query(low, high, k):
    # 查询给定区间给小于value的元素数目
    def query(cur, low, high, value):
        if tree[cur].block[0] > high or tree[cur].block[1] < low:return 0 # 无交集
        if low <= tree[cur].block[0] and tree[cur].block[1] <= high: # 包含当前结点
            return search(tree[cur].sorted_arr, value)

        # 递归操作左右子树
        return query(tree[cur].lchild, low, high, value) + query(tree[cur].rchild, low, high, value)

    # 二分枚举查找
    small, big = min(data), max(data)
    while small < big:
        mid_value = (small + big) >> 1
        result = query(0, low, high, mid_value)
        if result >= k: big = mid_value
        else: small = mid_value + 1
    return small

if __name__ == '__main__':
    # 输入数据
    str_data = input('Input the data separated by space:').split(' ')
    data = [int(x) for x in str_data]
    tmp_data, node_num, tree = data[:], len(data), []

    # 建立线段树
    for i in range(2 * node_num - 1):
        tree.append(Node())
    build_tree(node_num)
    # print_tree()

    # 交互查询
    while input('输入n退出,否则继续查询:') != 'n':
        low, high, k = [int(x) for x in input('请输入查询区间和K值,以空格分开:').split(' ')]
        print('第%s小:' % k, query(low, high, k))