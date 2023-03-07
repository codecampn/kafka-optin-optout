export const Error = (props: ErrorProps) => {
  return (
    <div
      data-testid={`error.${props.scope}`}
      className="relative rounded border border-red-400 bg-red-100 px-4 py-3 text-red-700"
      role="alert"
    >
      <strong
        data-testid={`error.${props.scope}.heading`}
        className="font-bold"
      >
        {props.title}:
      </strong>

      <span
        data-testid={`error.${props.scope}.detail`}
        className="block pl-2 sm:inline"
      >
        {String(props.description)}
      </span>
    </div>
  );
};

export interface ErrorProps {
  scope: string;
  title: string;
  description: string;
}
