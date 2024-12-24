%min_val = -1; %Начало диапазона
%max_val = 1; %Конец диапазона
%num_points = 6; %Количество точек в векторе

%x1 = linspace(min_val, max_val, num_points);
%x2 = linspace(min_val, max_val, num_points);

%[X1, X2] = meshgrid(x1, x2);

%Y = (X1 - 1)^2 + (2 - X2^2);
%dlmwrite('training_data.dat', [X1(:), X2(:), Y(:)], 'delimiter', '\t');


% Строим поверхность
%figure('Name', 'Теоретическая функция');
%surf(X1, X2, Y);
%xlabel('X_1');
%ylabel('X_2');
%zlabel('f(X_1, X_2)');
%title('Теоретическая функция f(x1, x2) = ((x_1) - 1)^2 + (2 - x_2^2)');
%colorbar;

% Векторизуем данные
%X = [X1(:), X2(:)];
%y_true = Y(:);
%disp(y_true);

% Входные данные
X1 = [-1, -0.5, 0, 0.5, 1];
X2 = [-1, -0.5, 0, 0.5, 1];

% Параметры для функций принадлежности
sigma = 0.5; % Стандартное отклонение для gaussmf
mean1 = 0;   % Среднее значение для gaussmf
mean2 = 0;   % Среднее значение для gaussmf

% Вычисление функций принадлежности
% Gauss2mf
gauss2mf_values = zeros(length(X1), length(X2));
for i = 1:length(X1)
    for j = 1:length(X2)
        gauss2mf_values(i, j) = exp(-((X1(i) - mean1)^2 + (X2(j) - mean2)^2) / (2 * sigma^2));
    end
end

% Trimf
trimf_values = zeros(length(X1), length(X2));
a = -1; % Левый край
b = 0;  % Центральная точка
c = 1;  % Правый край
for i = 1:length(X1)
    for j = 1:length(X2)
        trimf_values(i, j) = max(0, min((X1(i) - a)/(b - a), (c - X1(i))/(c - b)));
    end
end

% Gaussmf
gaussmf_values = zeros(length(X1), length(X2));
for i = 1:length(X1)
    for j = 1:length(X2)
        gaussmf_values(i, j) = exp(-0.5 * ((X1(i) - mean1)/sigma)^2);
    end
end

% Отображение результатов
disp('Значения функции принадлежности gauss2mf:');
disp(gauss2mf_values);
disp('Значения функции принадлежности trimf:');
disp(trimf_values);
disp('Значения функции принадлежности gaussmf:');
disp(gaussmf_values);
