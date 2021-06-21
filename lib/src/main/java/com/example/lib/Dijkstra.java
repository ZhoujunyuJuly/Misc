package com.example.lib;

import java.util.Scanner;

/**
 * Created by zhoujunyu on 2021-06-19.
 */
public class Dijkstra {
    static final int N = 2501;
    static final int M = 6501;
    static final int INF = 0x3f3f3f3f;
    static int G[][] = new int[N][N];  //建立图
    static int dist[] = new int[N];   //起始点到每个结点的距离
    static int vis[] = new int[N];   //标记结点是否被遍历过
    static int path[] = new int[N];  //标记路径
    static int n,m;  //顶点数 边数
    //static int s, t;  //起点终点


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();
//        s = sc.nextInt();
//        t = sc.nextInt();
        Init();
        int c1,c2,w;
        for(int i = 0; i < m;i++) {
            c1 = sc.nextInt();
            c2 = sc.nextInt();
            w = sc.nextInt();
            if(w < G[c1][c2]) {
                G[c1][c2] = G[c2][c1] = w;
            }
        }
        Dijkstra();
        search(n - 1);
        for(int i = 0;i< path.length ; i++){
            System.out.println(path[i]);
        }
    }

    static void Init()  //初始化邻接矩阵
    {
        for(int i = 1; i <= n; i++) {
            for(int j = 1; j <= n; j++) {
                if(i == j) G[i][j] = 0;   //从当前点到当前点的距离为0  矩阵对角线
                else G[i][j] = INF;        //其他区域初始化为无穷大
            }
        }
    }

    static void Dijkstra()
    {
        for(int i = 1; i <= n; i++) {
            dist[i] = G[0][i];   //对起始点到i的距离的初始化
            path[i] = -1;		//路径全都初始化为-1
        }
        vis[0] = 1;  //起始点标记为1
        for(int i = 1; i <= n; i++) {    //每次找距离起点距离最小的值 v，对周围的结点距离起点的距离进行更新  遍历n次
            int min = INF,v = 0;		//取最小值
            //遍历所有的点  找到距离起点最小的点
            for(int j = 1; j <= n; j++) {
                if(vis[j] == 0 && dist[j] < min) {
                    min = dist[j];
                    v = j;  //每次找到距离起点最近的点
                }
            }
            vis[v] = 1;
            //松弛操作
            for(int k = 1; k <= n; k++) {  //遍历v的邻接点, 更新邻接点k的距离dist[k]
                if(vis[k] == 0 && dist[v] + G[v][k] < dist[k]) {
                    dist[k] = dist[v] + G[v][k];
                    path[k] = v;
                }
            }
        }
        // System.out.println(dist[n-1]);
    }

    //递归输出最短路径
    static void search(int x) {
        if(x == -1) {
            //System.out.print(x);
            return;
        }
        search(path[x]);
        System.out.print(x);
    }
}
